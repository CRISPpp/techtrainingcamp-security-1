import {connect} from "react-redux";
import {Component} from "react";
import TempMainPageUI from "./TempMainPageUI";
import ReqPath from "../../api/url";
import { message} from "antd";
import axios from "axios";
import {HTTPCode} from "../../constant";

class TempMainPage extends Component {

  logout = () => {
    const request = {
      url: ReqPath.logout,
      method: 'put',
    };
    axios(request).then(res => {
      let response = res.data;
      if (response.metaInfo.status === HTTPCode.ok) {
        message.success({content: "登出成功", duration: 5})
        sessionStorage.removeItem("isLogin");
        window.location.href = '/#/login';
      } else {
        message.error({content: "登出失败：" + response.metaInfo.msg, duration: 5})
      }
    }).catch(res => {
      message.error({content: "登出失败：" + res, duration: 5})
    })
  }

  cancelAccount=()=>{
    const request = {
      url: ReqPath.cancelAccount,
      method: 'put',
    };
    axios(request).then(res => {
      let response = res.data;
      if (response.metaInfo.status === HTTPCode.ok) {
        message.success({content: "注销成功", duration: 5})
        sessionStorage.removeItem("isLogin");
        window.location.href = '/#/login';
      } else {
        message.error({content: "注销失败：" + response.metaInfo.msg, duration: 5})
      }
    }).catch(res => {
      message.error({content: "注销失败：" + res, duration: 5})
    })
  }

  render() {
    return (
      <div>
        <TempMainPageUI logout={this.logout} cancelAccount={this.cancelAccount}/>
      </div>
    )
  }
}

const stateToProps = (state) => {
  return {};
};

const dispatchToProps = (dispatch) => {
  return {};
};

export default connect(stateToProps, dispatchToProps)(TempMainPage);
