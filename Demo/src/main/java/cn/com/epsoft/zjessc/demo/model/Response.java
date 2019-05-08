package cn.com.epsoft.zjessc.demo.model;

/**
 * @author 启研
 * @created at 2018/11/23 14:48
 */
public class Response<E> {

    public boolean success;
    public String message;
    public String token;
    public E body;

    public String code;
    public String errCode;
    public String msg;
    /*
     * {
     *     "code": "1",
     *     "errCode": "ILLEGAL_ARGUMENT",
     *     "msg": "参数校验不正确"
     * }
     */

    public Response(Response r) {
        this.success = r.success;
        this.message = r.message;
    }
}
