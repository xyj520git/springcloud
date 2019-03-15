package com.xyj.exception;


import com.xyj.builder.ResultEntity;

/**
 * 4 开头,没有进入方法执行
 * 5 开头,数据问题导致不能继承执行
 */
public enum SystemMessage implements ExceptionCause<BusinessException> {
    OK(0,"成功"),
    ARGS_NULL(4000,"参数错误,必填参数 [%s]"),
    CALL_MODUL_FAIL(5004,"%s 模块调用错误")
    ;
    ResultEntity resultEntity = new ResultEntity();

    private SystemMessage(int returnCode, String message){
        resultEntity.setReturnCode(returnCode+"");
        resultEntity.setMessage(message);
    }


    @Override
    public BusinessException exception(Object...args) {
        return BusinessException.create(this,args);
    }

    @Override
    public ResultEntity result() {
        return resultEntity;
    }

    /**
     * 自定义消息的结果返回
     * @param args
     * @return
     */
    public ResultEntity result(Object ... args){
        String message = resultEntity.getMessage();
        resultEntity.setMessage(String.format(message,args));
        return resultEntity;
    }

    public String getReturnCode(){
        return resultEntity.getReturnCode();
    }
}
