package common;

public enum RetCode {
    OK (1000, "成功"), // 成功

    UNKNOWN_ERROR (0, "未知错误"), // 未知错误

    // 系统公共返回码
    INVALID_PARAMS (100001, "参数错误"), // 参数错误
    AUTH_FAILED (100002, "验证码错误"), // 校验错误
    PROTOCOL_ERROR (100003, "协议错误"), // 协议错误
    SERVER_ERROR (100004, "服务异常"), // 服务器错误
    NOT_SUPPORTED (100005, "重复点击"), // 重复点击
    UPLOAD_FILE_ERROR(100006, "文件上传失败"),

    // 业务公共返回码
    RECORD_NOT_FOUND (200001, "记录不存在"), // 找不到记录
    RECORD_EXISTS (200002, "记录已存在"), // 记录已存在
    PERMISSION_DENIED (200003, "权限不足"), // 权限不足
    SERVICE_DISABLED(200004, "服务关闭"), // 服务关闭
    RECORD_DISABLED (200005, "记录已禁用"), // 记录已禁用
    CODE_EXISTS (200006, "编码已存在"),  // 编码重复
    NAME_EXISTS (200007, "名称已存在"),  // 名称重复

    // 账号返回码
    ACCOUNT_NOT_FOUND (300001, "账号不存在"), // 账号不存在
    ACCOUNT_ISUSED (300002, "账号已存在"), // 账号不存在
    MOBILE_EXISTS (300002, "手机号已存在"), // 手机号已绑定
    MOBILE_NOT_FOUND (300003, "手机号已存在"), // 手机号不存在

    DOCTOR_NOT_FOUND(300011, "医生不存在"), // 医生不存在
    DOCTOR_EXIST(3000012, "医生已存在"), // 医生存在

    PATIENT_NOT_FOUND(300021, "患者不存在"), // 患者不存在
    PATIENT_EXIST(300022, "患者已存在"), // 患者存在

    REPRESENT_NOT_FOUND(300031, "代表不存在"), // 代表不存在
    REPRESETN_EXIST(300032, "代表存在"), // 代表存在

    // 排期返回码
    SCHED_NOT_FOUND(400001, "排期不存在"),
    SCHED_BASEINFO_NOT_FOUND(400002, "预约基础信息不存在"),
    SCHED_QUOTA_LESS_THAN_USED(400003, "放号数小于已预约数"), // 放号数小于已预约数
    SCHED_QUOTA_RUNOUT(400004, "预约已满"), // 预约已满
    SCHED_QUOTA_USED(400005, "排期已有患者预约,不允许删除"), // 已预约数,不能取消
    SCHED_CANCEL(400006, "预约取消失败"), // 订单取消失败

    // 订单返回码
    ORDER_NOT_FOUND(500001, "订单不存在"), // 订单不存在
    ORDER_STATUS_CONFLICT(500002, "订单状态冲突"),
    ORDER_CANNOT_PAY(500003, "订单不可支付"),
    ORDER_IS_PAY(500004,"订单已支付"),
    MEDICINE_EXIST(500010, "药品已存在"),
    MEDICINE_NOT_FOUND(500011, "药品不存在"),
    GOODS_NOT_FOUND(500012, "产品不存在"),
    TREAT_APPOINTED(500021, "已预约过该班次"),
    TREAT_ADDR_INVALID(500022, "无效的预约地址"),
    NOT_ONLINE_TREAT(500023, "非线上预约"),
    OUT_OFF_STOCK(500024, "库存不足"),
    ORDER_CANCEL(5000025, "订单取消失败"), // 订单取消失败
    

    // 送货地址返回码
    PATIENT_ADDR_NOT_SET(600001, "患者送货地址未设置"), // 患者送货地址未设置
    DELIVER_PRICE_UNKNOWN(600002, "快递费未知"), // 快递费未知

    // 用户关系返回码
    REPRESENT_ADD_DOCTOR_CONFLICT(700001, "代表添加医生冲突"), // 客户代表添加医生冲突
    DOCTOR_ADD_DOCTOR_CONFLICT(700002, "医生添加医生冲突"), // 医生添加医生冲突

    // 用户模块
    USER_ACCOUNT_EXIST(800001, "用户账号名不存在或者密码错误"),
    USER_ACCOUNT_ILLEGAL(80002, "用户账号名不合法"),
    USER_NOTFOUND(80002, "用户未登录"),

    // 字典管理模块
    DICT_CODE_EXIST(900001, "字典编码已存在"),
    DICT_CODE_ILLEGAL(900002, "字典编码不合法"),

    // 诊室管理模块
    ROOM_CODE_EXIST(110001, "诊室编号已存在"),
    ROOM_CODE_ILLEGAL(110002, "诊室编号不合法"),

    // 角色管理模块
    ROLE_NAME_EXIST(120001, "角色名已存在"),
    
    
    ;

	private int code;
	private String msg;
	
	RetCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getMsg() {
		return msg;
	}

    public static RetCode parse(int value) {
        for (RetCode rc : RetCode.values()) {
            if (rc.getCode() == value) {
                return rc;
            }
        }
        return UNKNOWN_ERROR;
    }
	
}
