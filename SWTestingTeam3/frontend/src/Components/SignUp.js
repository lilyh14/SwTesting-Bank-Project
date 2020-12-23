import React, { useState } from "react";
import { Link } from "@reach/router";
import { auth, generateUserDocument } from "../firebase";
import axios from "axios";


const SignUp = () => {
  //const url = "localhost:8080"

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [verifyPassword, setVerifyPassword] = useState("");
  const [fullName, setFullName] = useState("");
  const [address, setAddress] = useState("");
  const [error, setError] = useState(null);

  const createUserWithEmailAndPasswordHandler = async (event, _email, password, verifyPassword) => {
    event.preventDefault();
    if(password !== verifyPassword)
      alert("Passwords don't match");
    else{
    try{
        const {user} = await auth.createUserWithEmailAndPassword(email, password);

//Below is necessary in order to retrieve an api key.
/*
        let axiosConfig = {
          headers: {
              'Content-Type': 'application/json',
              'Accept': 'application/json',
              'Access-Control-Allow-Origin': '*'
          }
        };

        axios.post(`http://${url}/firestoreAuth`, {
             orgName: "",
             industry: "",
             fullName: fullName,
             email: _email
         }, axiosConfig);
*/
         generateUserDocument(user, {fullName});
      }
      catch(error){
        setError('Error Signing up with email and password');
      }
    setEmail("");
    setPassword("");
    setFullName("");
    setAddress("");
    setVerifyPassword("");
   } };
  const onChangeHandler = event => {
    const { name, value } = event.currentTarget;
    if (name === "userEmail") {
      setEmail(value);
    } else if (name === "userPassword") {
      setPassword(value);
    }else if (name === "verifyPassword") {
      setVerifyPassword(value);
    }else if (name === "fullName") {
      setFullName(value);
    }else if (name === "address") {
      setAddress(value);
    }
  };
  return (
    <div className="mt-8">
      <h1 className="text-3xl mb-2 text-center font-bold">Sign Up</h1>
      <div className="border border-blue-400 mx-auto w-11/12 md:w-2/4 rounded py-8 px-4 md:px-8">
        {error !== null && (
          <div className="py-4 bg-red-600 w-full text-white text-center mb-3">
            {error}
          </div>
        )}
        <form className="">
          <label htmlFor="fullName" className="block">
            Full Name:
          </label>
          <input
            type="text"
            className="my-1 p-1 w-full "
            name="fullName"
            value={fullName}
            placeholder="E.g: Gator "
            id="fullName"
            onChange={event => onChangeHandler(event)}
          />
          <label htmlFor="userEmail" className="block">
            Email:
          </label>
          <input
            type="email"
            className="my-1 p-1 w-full"
            name="userEmail"
            value={email}
            placeholder="E.g: gator@gmail.com"
            id="userEmail"
            onChange={event => onChangeHandler(event)}
          />
           <label htmlFor="address" className="block">
            Address:
          </label>
          <input
            type="text"
            className="my-1 p-1 w-full "
            name="address"
            value={address}
            placeholder="E.g: 2050 Park Way "
            id="address"
            onChange={event => onChangeHandler(event)}
          />

          <label htmlFor="userPassword" className="block">
            Password:
          </label>
          <input
            type="password"
            className="mt-1 mb-3 p-1 w-full"
            name="userPassword"
            value={password}
            placeholder="Your Password"
            id="userPassword"
            onChange={event => onChangeHandler(event)}
          />
          <label htmlFor="userPassword" className="block">
           Verify Password:
          </label>
          <input
            type="password"
            className="mt-1 mb-3 p-1 w-full"
            name="verifyPassword"
            value={verifyPassword}
            placeholder="Your Password"
            id="verifyPassword"
            onChange={event => onChangeHandler(event)}
          />
          <button
            className="bg-green-400 hover:bg-green-500 w-full py-2 text-white"
            onClick={event => {
              createUserWithEmailAndPasswordHandler(event, email, password, verifyPassword);
            }}
          >
            Sign up
          </button>
        </form>
        <p className="text-center my-3">or</p>
        <p className="text-center my-3">
          Already have an account?{" "}
          <Link to="/" className="text-blue-500 hover:text-blue-600">
            Sign in here
          </Link>
        </p>
      </div>
    </div>
  );
};
export default SignUp;
