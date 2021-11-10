import {connect} from "react-redux";
import {Component} from "react";
import TempRegisterFormUI from "./TempRegisterFormUI";
import {getDeviceId, getIp} from "../../../utils/EnvironmentTools";
import ReqPath from "../../../api/url";
import {message} from "antd";
import axios from "axios";
import {HTTPCode} from "../../../constant";
import {setNeedCaptcha} from "../../../redux/actions/LoginAction";

class TempRegisterForm extends Component {

  componentDidMount() {

  }

  checkResponseNeedCaptcha = (data) => {
    if (data.metaInfo.status === HTTPCode.danger ||
      (data.data != null && data.data.decisionType >= 1)) {
      this.props.setNeedCaptcha(true);
      setTimeout(() => {
        this.props.getCaptcha();
      }, 600);
    } else {
      this.props.setNeedCaptcha(false);
    }
  }

  submitRegister = (values) => {
    let data = {
      ...values,
      "verifyToken": this.props.captchaToken,
      "ip": getIp(),
      "deviceId": getDeviceId()
    };
    const request = {
      url: ReqPath.register,
      method: 'post',
      params: data
    };
    let registerKey = "REGISTER_KEY";
    message.loading({content: "注册中...", key: registerKey});
    axios(request).then(res => {
      let response = res.data;
      this.checkResponseNeedCaptcha(response);
      if (response.metaInfo.status === HTTPCode.ok) {
        message.success({content: "注册成功", key: registerKey, duration: 5})
        setTimeout(function () {
          window.location.href = '/#/login';
        }, 1000);
      } else {
        message.error({content: "注册失败：" + response.metaInfo.msg, key: registerKey, duration: 5})
      }
    }).catch(res => {
      message.error({content: "注册失败：" + res, key: registerKey, duration: 5})
    })
  }

  render() {
    return (
      <div>
        <TempRegisterFormUI submitRegister={this.submitRegister}
                            getCaptcha={this.props.getCaptcha}
                            captchaImg={this.props.captchaImg}
                            needCaptcha={this.props.needCaptcha}/>
      </div>
    )
  }
}

const stateToProps = (state) => {
  return {
    captchaImg: state.LoginReducer.captchaImg,
    captchaToken: state.LoginReducer.captchaToken,
    needCaptcha: state.LoginReducer.needCaptcha,
  };
};

const dispatchToProps = (dispatch) => {
  return {
    setNeedCaptcha(needCaptcha) {
      dispatch(setNeedCaptcha(needCaptcha));
    }
  };
};

export default connect(stateToProps, dispatchToProps)(TempRegisterForm);
