import React from "react";
import {Form, Input, Button, Row, Col, Image, Popover} from "antd";
import {UserOutlined, LockOutlined, SafetyCertificateOutlined} from "@ant-design/icons";
import "./LoginForm.css"
import {Link} from "react-router-dom";


const LoginFormUI = (props) => {
  return (
    <div style={{padding: '5px'}}>
      <Form
        name="normal_login"
        className="login-form"
        layout="vertical"
        requiredMark={false}
        initialValues={{
          remember: true,
        }}
        onFinish={props.submitLogin}
      >
        <Form.Item
          className="FormItem"
          name="username"
          label={props.input1Label}
          rules={[
            {
              required: true,
              message: "请输入用户名",
            },
          ]}
        >
          <Input
            prefix={<UserOutlined className="site-form-item-icon"/>}
            placeholder={props.input1Placeholder}
          />
        </Form.Item>
        <Form.Item
          name="password"
          label={props.input2Label}
          rules={[
            {
              required: true,
              message: "请输入密码",
            },
          ]}
        >
          <Input
            prefix={<LockOutlined className="site-form-item-icon"/>}
            type="password"
            placeholder={props.input2Placeholder}
          />
        </Form.Item>

        <Form.Item label="验证码" hidden={!props.needCaptcha}>
          <Row>
            <Col flex="160px">
              <Form.Item
                name="verifyCode"
                noStyle
                rules={[{required: props.needCaptcha, message: "请输入验证码",},]}
              >
                <Input
                  prefix={<SafetyCertificateOutlined className="site-form-item-icon"/>}
                  placeholder="请输入数字验证码"/>
              </Form.Item>

            </Col>
            <Col flex="auto"/>
            <Col flex="none">
              <Popover content={<div>点击刷新</div>}>
                <Image src={props.captchaImg} height='34px' preview={false} onClick={props.getCaptcha} alt="暂停访问">
                </Image>
              </Popover>
              <div style={{color: '#999999', textAlign: 'center'}}>看不清?点击刷新</div>
            </Col>
          </Row>
        </Form.Item>

        <Form.Item style={{textAlign: 'center'}}>
          <Button type="primary" htmlType="submit">登录</Button>
          <br/>
          <Button type="link" style={{marginTop:'10px'}}><Link to="/register">没有账号？立即注册</Link></Button>
        </Form.Item>
      </Form>
    </div>
  );
}

export default LoginFormUI;