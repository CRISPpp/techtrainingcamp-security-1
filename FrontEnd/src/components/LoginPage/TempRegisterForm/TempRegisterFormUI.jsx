import { Card, Col, Row, Button, Form, Input, Popover, Image} from "antd";
import {LockOutlined, SafetyCertificateOutlined, UserOutlined, PhoneOutlined} from "@ant-design/icons";
import React from "react";
import {Link} from "react-router-dom";


const TempRegisterUI = (props) => {
  return (
    <div>
      <Card style={{width: '500px', margin: "50px auto"}}>
        <h2 style={{textAlign:"center"}}>用户注册</h2>
        <div style={{marginTop: "10px"}}>
          <Form
            name="normal_login"
            className="login-form"
            layout="vertical"
            // requiredMark={false}
            initialValues={{
              remember: true,
            }}
            onFinish={props.submitRegister}
          >
            <Form.Item
              name="username"
              label="用户名"
              rules={[
                {
                  required: true,
                  message: "请输入用户名",
                },
              ]}
            >
              <Input
                prefix={<UserOutlined/>}
                placeholder="请设置用户名"
              />
            </Form.Item>

            <Form.Item
              name="phoneNumber"
              label="手机号"
              hasFeedback
              rules={[
                {
                  required: true,
                  message: "请输入手机号",
                },
                () => ({
                  validator(_, value) {
                    let regExp=/^1[0-9]{10}$/;
                    if(regExp.test(value))
                      return Promise.resolve();
                    return Promise.reject(new Error('请输入一个合法的手机号'));
                  },
                }),
              ]}
            >
              <Input
                prefix={<PhoneOutlined rotate="90"/>}
                placeholder="请输入手机号"
              />
            </Form.Item>

            <Form.Item
              name="password"
              label="密码"
              hasFeedback
              rules={[
                {
                  required: true,
                  message: "请输入密码",
                },
                () => ({
                  validator(_, value) {
                    let regExp=/^(?=.*\d)(?=.*[a-zA-Z])[\da-zA-Z~!@#$%^&*]{6,18}$/;
                    if(regExp.test(value))
                      return Promise.resolve();
                    return Promise.reject(new Error('密码不合规范：6-18位，且必须包含数字、英文字母，不包含特殊字符'));
                  },
                }),
              ]}
            >
              <Input
                prefix={<LockOutlined/>}
                type="password"
                placeholder="请设置密码（长度6-18位，且必须包含数字、英文字母）"
              />
            </Form.Item>

            <Form.Item
              name="passwordAgain"
              label="确认密码"
              dependencies={['password']}
              hasFeedback
              rules={[
                {
                  required: true,
                  message: "请再次输入密码",
                },
                ({ getFieldValue }) => ({
                  validator(_, value) {
                    if (!value || getFieldValue('password') === value) {
                      return Promise.resolve();
                    }
                    return Promise.reject(new Error('两次输入的密码不同'));
                  },
                }),
              ]}
            >
              <Input
                prefix={<LockOutlined/>}
                type="password"
                placeholder="请再次输入密码"
              />
            </Form.Item>

            <Form.Item label="验证码"
                       hidden={props.needCaptcha===false}
              required={false}
            >
              <Row>
                <Col flex="160px">
                  <Form.Item
                    name="captcha"
                    rules={[{required: props.needCaptcha, message: "请输入验证码",},]}
                  >
                    <Input
                      prefix={<SafetyCertificateOutlined/>}
                      placeholder="请输入数字验证码"/>
                  </Form.Item>

                </Col>
                <Col flex="auto"/>
                <Col flex="none">
                  <Popover content={<div>点击刷新</div>}>
                    <Image src={props.captchaImg} height='34px' preview={false} onClick={props.getCaptcha} alt="暂停访问">
                    </Image>
                  </Popover>
                  <span style={{color: '#999999', textAlign: 'center'}}>看不清?点击刷新</span>
                </Col>
              </Row>
            </Form.Item>

            <Form.Item style={{textAlign: 'center'}}>
              <Button
                type="primary"
                htmlType="submit"
              >
                注册
              </Button>
              <br/>
              <Button
                type="link"
                style={{marginTop: '10px'}}
              >
                <Link to="/login">
                  已有账号？直接登陆
                </Link>
              </Button>
            </Form.Item>
          </Form>
        </div>
      </Card>
    </div>
  );
};

export default TempRegisterUI;