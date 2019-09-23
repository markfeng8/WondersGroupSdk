/**
 * Created by x-sir on 19/09/23 :)
 * Function:家庭医生模块 js 调用 Android
 */
var payParameter = Object();
// 姓名
payParameter.name = null;
// 证件类型（01为身份证）
payParameter.idType = null;
// 证件号码
payParameter.idNum = null;
// 就诊卡类型（2为自费卡）
payParameter.cardType = null;
// 就诊卡号码
payParameter.cardNum = null;
// 机构（医院）名称
payParameter.orgName = null;
// 机构（医院）代码
payParameter.orgCode = null;

function getMessageFromForm(){
    payParameter.name = null;
    payParameter.idType = null;
    payParameter.idNum = null;
    payParameter.cardType = null;
    payParameter.cardNum = null;
    payParameter.orgName = null;
    payParameter.orgCode = null;

    var formElement = document.getElementById("messageForm");
    if(formElement != null){
        payParameter.name = document.getElementById("name").value;
        payParameter.idType = document.getElementById("idType").value;
        payParameter.idNum = document.getElementById("idNum").value;
        payParameter.cardType = document.getElementById("cardType").value;
        payParameter.cardNum = document.getElementById("cardNum").value;
        payParameter.orgName = document.getElementById("orgName").value;
        payParameter.orgCode = document.getElementById("orgCode").value;
    }else{
        alert("未找到form");
    }
}

/*
 * js 调用 Android 的方法，将所有要传递的参数封装成一个 json 传递过去
 */
function jsCallAndroidPayment() {
    getMessageFromForm();
    var json = "{name:" + payParameter.name + ",idType:" + payParameter.idType + ",idNum:" + payParameter.idNum + ",cardType:" + payParameter.cardType
        + ",cardNum:" + payParameter.cardNum + ",orgName:" + payParameter.orgName + ",orgCode:" + payParameter.orgCode + "}";

    // 由于对象映射，所以调用 nativeMethod 对象等于调用 Android 映射对象的方法
    nativeMethod.toPayment(json);
}