import {Button, Popconfirm, Tag} from "antd";
import {LogoutOutlined, UserDeleteOutlined} from '@ant-design/icons';

const TempMainPageUI = (props) => {
  return (
    <div>
      <div style={{textAlign: "center", marginTop: "100px"}}>
        <div>
          <Tag color="geekblue" style={{width: '300px', height: '100px'}}>
            <div style={{fontSize: '40px', paddingTop: '14%'}}>
              这是个主页
            </div>
          </Tag>
        </div>
        <div style={{marginTop: '150px'}}>
          <Popconfirm title="确认退出登录？" okText="确认" cancelText="取消" onConfirm={()=>{props.logout()}}>
            <Button type="primary" shape="round" icon={<LogoutOutlined/>} size="large"
                    style={{marginRight: '60px'}}>退出登录</Button>
          </Popconfirm>
          <Popconfirm title="确认注销账户？" okText="确认" cancelText="取消" onConfirm={()=>{props.cancelAccount()}}>
            <Button type="primary" shape="round" icon={<UserDeleteOutlined/>} size="large">注销账户</Button>
          </Popconfirm>
        </div>
      </div>


    </div>
  );
};

export default TempMainPageUI;