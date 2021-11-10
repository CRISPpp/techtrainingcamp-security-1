import {connect} from "react-redux";
import {Component} from "react";
import LoginFormUI from "./LoginFormUI";
import {getDeviceId, getIp} from "../../../utils/EnvironmentTools";
import ReqPath from "../../../api/url";
import axios from "axios";
import {message} from "antd";
import {HTTPCode} from "../../../constant";
import {setNeedCaptcha} from "../../../redux/actions/LoginAction";

class LoginForm extends Component {
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

  submitLogin = (values) => {
    let data = {
      ...values,
      "verifyToken": this.props.captchaToken,
      "ip": getIp(),
      "deviceId": getDeviceId()
    };
    const request = {
      url: ReqPath.login,
      method: 'post',
      params: data
    };
    let loginKey = "LOGIN_KEY";
    message.loading({content: "登录中...", key: loginKey});
    axios(request).then(res => {
      let response = res.data;
      console.log(response);
      this.checkResponseNeedCaptcha(response);
      if (response.metaInfo.status === HTTPCode.ok) {
        message.success({content: "登录成功", key: loginKey, duration: 5})
        sessionStorage.setItem("isLogin", "true");
        window.location.href = '/';
      } else {
        message.error({content: "登录失败：" + response.metaInfo.msg, key: loginKey, duration: 5})
      }
    }).catch(res => {
      message.error({content: "登录失败：" + res, key: loginKey, duration: 5})
    })
  }

  render() {
    return (
      <div>
        <LoginFormUI
          input1Label={this.props.input1Label}
          input1Placeholder={this.props.input1Placeholder}
          input2Label={this.props.input2Label}
          input2Placeholder={this.props.input2Placeholder}
          captchaImg={this.props.captchaImg}
          getCaptcha={this.props.getCaptcha}
          submitLogin={this.submitLogin}
          needCaptcha={this.props.needCaptcha}
        />

        {/*<div style={{textAlign:"center"}}>*/}
        {/*<Button danger type="primary" onClick={() => {*/}
        {/*  this.props.getCaptcha()*/}
        {/*}}>模拟刷请求</Button>*/}
        {/*</div>*/}
      </div>
    );
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

export default connect(stateToProps, dispatchToProps)(LoginForm);
