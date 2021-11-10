import React from "react";
import {Redirect, Route, Switch, withRouter} from "react-router-dom";

class MyRoute extends React.Component {
  componentWillMount() {
    // TODO 放进constant
    let isAuthenticated = (sessionStorage.getItem("userType") === this.props.userType);
    let isLogin = (sessionStorage.getItem("isLogin") === "true");
    // this.setState({isAuthenticated: isAuthenticated})
    // TODO FINISH SETSTATE
    this.setState({isAuthenticated: true, isLogin: isLogin})
  }

  render() {
    let {
      component: Component,
      path = "/",
      exact = false,
      strict = false,
      needLogin = true,
      isPrivate = false
    } = this.props;
    if (needLogin) {
      if (isPrivate) {
        return (
          this.state.isAuthenticated ?
            (<Route path={path} exact={exact} strict={strict} render={(props) => (<Component {...props} />)}/>) :
            (<Switch>
              <Redirect from={path} to="/403"/>
            </Switch>)
        )
      } else {
        return this.state.isLogin ?
          (<Route path={path} exact={exact} strict={strict} render={(props) => (<Component {...props} />)}/>) :
          (<Switch>
            <Redirect from={path} to="/login"/>
          </Switch>)
      }
    } else {
      return (<Route path={path} exact={exact} strict={strict} render={(props) => (<Component {...props} />)}/>);
    }
  }
}

export default withRouter(MyRoute);