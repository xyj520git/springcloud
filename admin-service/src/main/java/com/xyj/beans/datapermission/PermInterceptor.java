package com.xyj.beans.datapermission;

import com.xyj.Mapper;
import com.xyj.annoation.DataPermAspect;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Properties;

@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class,Integer.class }) })
public class PermInterceptor implements Interceptor {
 	CCJSqlParserManager parserManager = new CCJSqlParserManager();

 	private Log logger = LogFactory.getLog(getClass());
 	private static  final  String countSuffix = "_COUNT";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
        boolean needEnhance = false;

        StatementHandler handler = (StatementHandler)invocation.getTarget();
		//由于mappedStatement为protected的，所以要通过反射获取
		MetaObject statementHandler = SystemMetaObject.forObject(handler);
		//mappedStatement中有我们需要的方法id
		MappedStatement mappedStatement = (MappedStatement) statementHandler.getValue("delegate.mappedStatement");
		//获取方法id
		String statementId = mappedStatement.getId();
		String targetMapperMethodId = statementId;
		SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

		//如果不是 select ,也不需要增强
        if(sqlCommandType != SqlCommandType.SELECT){
            return invocation.proceed();
        }

        Method enhanceMethod = null;
        DataPermAspect dataPermAspect = null;
        if(statementId.endsWith(countSuffix)) {
            targetMapperMethodId = statementId.substring(0, statementId.lastIndexOf('_'));
        }

        //获取当前执行 mapper 方法和权限注解
        int lastIndexOf = targetMapperMethodId.lastIndexOf('.');
        if(lastIndexOf != -1){
            Class<? extends Mapper> clazz = ClassUtils.getClass(targetMapperMethodId.substring(0,lastIndexOf));
            String methodName = targetMapperMethodId.substring(lastIndexOf+1);
            Method[] methodsWithAnnotation = MethodUtils.getMethodsWithAnnotation(clazz,DataPermAspect.class);
            if(methodsWithAnnotation.length > 0 ){
                //TODO 每次需要遍历所有方法
                for (Method method : methodsWithAnnotation) {
                    String currMethodName = method.getName();
                    if(methodName.equals(currMethodName)){
                        needEnhance = true;
                        enhanceMethod = method;
                        dataPermAspect = method.getAnnotation(DataPermAspect.class);
                        break;
                    }
                }
            }
        }

        if(!needEnhance){
            //不需要增强的 sql ,直接返回执行
            return invocation.proceed();
        }

        //增强 sql 功能 ,增加数据权限
		BoundSql boundSql = handler.getBoundSql();
		String sql = boundSql.getSql();
        Select select = (Select)parserManager.parse(new StringReader(sql));
        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect)selectBody;

        //TODO 获取登陆用户的
        String orgNo = "100101";


        //增强 sql
        Expression where = plainSelect.getWhere();
        if(where == null){
            // 如果 没有 where ,添加一个为真为表达式
            where = new StringValue("1");
        }
        Expression parenthesis = new Parenthesis(where);
        LikeExpression likeExpression = new LikeExpression();
        String column = "orgNo";
        if(StringUtils.isNotBlank(dataPermAspect.tablePerix())){
            column = dataPermAspect.tablePerix()+"."+column;
        }
        likeExpression.setLeftExpression(new Column(column));
        likeExpression.setRightExpression(new StringValue(orgNo+"%"));
        AndExpression andExpression = new AndExpression(parenthesis, likeExpression);
        plainSelect.setWhere(andExpression);
        //将增强后的sql放回
        statementHandler.setValue("delegate.boundSql.sql",select.toString());

        if(  dataPermAspect.showSql()){
            logger.info(statementId+": "+select.toString());
        }
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
