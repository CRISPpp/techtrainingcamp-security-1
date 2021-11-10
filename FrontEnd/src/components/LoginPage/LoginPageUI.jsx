import {Tabs, Layout, Divider, Card, Tag} from "antd";
import LoginForm from "./LoginForm/LoginForm";
import logoURL from "../../asset/images/logo.png";
import backgroundURL from "../../asset/images/background.png";
import "./LoginPage.css"
import TempRegisterForm from "./TempRegisterForm/TempRegisterForm";
import {Switch} from "react-router-dom";
import MyRoute from "../../utils/MyRoute.js";

const {Header} = Layout;
const {TabPane} = Tabs;

const LoginPageUI = (props) => {
  return (
    <div style={{width: '100%', minHeight: '100%', position: 'absolute'}}>
      <Header style={{background: "#ffffff"}}>
        <img
          id="titleLogo"
          width={35}
          height={35}
          className="logoImg"
          src={logoURL}
          style={{float: "left", marginTop: "15px"}}
          alt="加载失败"
        />
        <span
          id="titleText"
          style={{
            float: "left",
            fontSize: "20px",
            fontWeight: "bold",
            marginLeft: "10px",
          }}
        >
              风控账号系统
          <Tag color="geekblue" style={{marginLeft: '10px'}}>演示版</Tag>
        </span>
      </Header>
      <Divider style={{margin: '0px'}}/>
      <div style={{
        display: 'inline-block',
        width: '100%',
        minHeight: '650px',
        background: `url("${backgroundURL}") center center / cover no-repeat`,
      }}>
        <Switch>
          <MyRoute path="/login" needLogin={false} component={() => (
            <div>
              <div id="LoginCard">
                <Card>
                  <Tabs
                    id="CardTabs"
                    defaultActiveKey="1"
                    centered
                    animated
                  >
                    <TabPane tab="用户登录" key="1">
                      <div style={{marginTop: "10px"}}>
                        <LoginForm
                          input1Label="用户名"
                          input1Placeholder="请输入用户名"
                          input2Label="密码"
                          input2Placeholder="请输入密码"
                          getCaptcha={props.getCaptcha}
                        />
                      </div>
                    </TabPane>
                    {/*<TabPane tab="管理员登录" key="2">*/}
                    {/*  <div style={{marginTop: "10px"}}>*/}
                    {/*    <LoginForm*/}
                    {/*      input1Label="管理员用户名"*/}
                    {/*      input1Placeholder="请输入管理员用户名"*/}
                    {/*      input2Label="密码"*/}
                    {/*      input2Placeholder="请输入密码"*/}
                    {/*    />*/}
                    {/*  </div>*/}
                    {/*</TabPane>*/}
                  </Tabs>
                </Card>
              </div>
            </div>
          )}/>
          <MyRoute path="/register" needLogin={false} component={() => {
            return <TempRegisterForm getCaptcha={props.getCaptcha}/>
          }}/>
        </Switch>
      </div>
      <div style={{textAlign: 'center', paddingTop: '10px', paddingBottom: '10px'}}>
        风控账号系统 ©2021 字节跳动技术训练营-第一组
      </div>
    </div>
  );
};

export default LoginPageUI;