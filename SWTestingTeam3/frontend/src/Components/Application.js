import React from "react";
import { Router } from "@reach/router";
import { useContext } from "react";
import SignIn from "./SignIn";
import SignUp from "./SignUp";
import Dashboard from "./Dashboard";
import AdminLogs from "./AdminLogs";
import { UserContext } from "../Providers/UserProvider";
import {auth, logAPI, updateBalance} from "../firebase";
//add import for dashboard
function Application() {
  const user = useContext(UserContext);

  let toBeDisplayed;

  if (user && user.fullName == "admin") {
    toBeDisplayed = <AdminLogs />
  }
  else if (user && user.fullName != "admin") {
    toBeDisplayed = <Dashboard />
  }
  else {
    toBeDisplayed = <Router><SignUp path="signUp" /><SignIn path="/" /></Router>
  }

  return (
      <div>{toBeDisplayed}</div>

  );
}
export default Application;
