import {connect} from "react-redux";
import {Component} from "react";
import LoginPageUI from "./LoginPageUI";
import axios from "axios";
import {message} from "antd";
import ReqPath from "../../api/url";
import {saveCaptchaToken, setCaptchaImg, setNeedCaptcha} from "../../redux/actions/LoginAction";
import {getDeviceId, getIp} from "../../utils/EnvironmentTools";
import {HTTPCode} from "../../constant";

class LoginPage extends Component {

  componentDidMount() {
    this.askNeedCaptcha()
  }

  askNeedCaptcha = () => {
    const request = {
      url: ReqPath.needCaptcha,
      method: 'get',
      params: {
        'deviceId': getDeviceId(),
        'ip': getIp(),
      }
    };
    axios(request).then(res => {
      const response = res.data;
      if (response.metaInfo.status !== HTTPCode.ok) {
        this.getCaptcha();
        this.props.setNeedCaptcha(true);
      } else {
        this.props.setNeedCaptcha(false);
      }
    }).catch(res => {

    })
  }


  getCaptcha = () => {
    const request = {
      url: ReqPath.getCaptcha,
      method: 'post',
      params: {
        'deviceId': getDeviceId(),
        'ip': getIp(),
      }
    };
    axios(request).then(res => {
      const response = res.data;
      if (response.metaInfo.status === HTTPCode.danger || response.data.decisionType >= 1) {
        this.props.setNeedCaptcha(true);
      } else {
        this.props.setNeedCaptcha(false);
      }
      this.props.setCaptchaImg(response.data.verifyImg);
      this.props.saveCaptchaToken(response.data.verifyToken);
      if (response.metaInfo.status !== HTTPCode.ok
        // && response.metaInfo.status !== HTTPCode.danger
      ) {
        message.warning({content: response.metaInfo.msg, duration: 5});
      }
    }).catch(res => {
      console.log("获取验证码失败", res);
    })
  }

  render() {
    return (
      <div>
        <LoginPageUI getCaptcha={this.getCaptcha}/>
      </div>
    )
  }
}

const stateToProps = (state) => {
  return {};
};

const dispatchToProps = (dispatch) => {
  return {
    setCaptchaImg(captchaImg) {
      dispatch(setCaptchaImg(captchaImg));
    },
    saveCaptchaToken(captchaToken) {
      dispatch(saveCaptchaToken(captchaToken));
    },
    setNeedCaptcha(needCaptcha) {
      dispatch(setNeedCaptcha(needCaptcha));
    }
  };
};

export default connect(stateToProps, dispatchToProps)(LoginPage);
