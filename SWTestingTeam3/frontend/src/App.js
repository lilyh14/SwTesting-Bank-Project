import logo from './logo.svg';
import './App.css';
import React from "react";
import Application from "./Components/Application";
import UserProvider from "./Providers/UserProvider";
import dotenv from 'dotenv';

function App() {
    dotenv.config();
    
  return (
    <UserProvider>
      <Application />
    </UserProvider>
  );
}
export default App;
