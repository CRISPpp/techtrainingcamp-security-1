import "./App.less";
import LoginPage from "./components/LoginPage/LoginPage";
import MyRoute from "./utils/MyRoute";
import TempMainPage from "./components/TempMainPage/TempMainPage";
import {ConfigProvider} from "antd";


function App() {
  return (
    <div className="App">
      <ConfigProvider>
        <MyRoute path="/login" needLogin={false} component={LoginPage}/>
        <MyRoute path="/register" needLogin={false} component={LoginPage}/>
        <MyRoute exact path="/" component={TempMainPage}/>
      </ConfigProvider>
    </div>
  );
}

export default App;
