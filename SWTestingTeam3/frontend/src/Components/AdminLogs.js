import React, { Component, useContext, useState } from "react";
import { UserContext } from "../Providers/UserProvider";
import { Link } from "@reach/router";
import { auth, firestore } from "../firebase";
import { render } from "@testing-library/react";

class AdminLogs extends React.Component {
    constructor() {
        super();
        this.state = { data: [] };
    }

    async componentDidMount() {
        var tempList = []
        const logsRef = firestore.collection('API Calls');
        const querySnapshot = await logsRef.get();
        querySnapshot.forEach(doc => {
            let items = doc.data();
            tempList.push(items);
        })
        this.setState({ data : tempList});
    }

    renderTableData() {
        return this.state.data.map((log, index) => {
            return (
                <tr key={index}>
                    <td>{log.Date}</td>
                    <td>{log.api}</td>
                    <td>{log.UserID}</td>
                    <td>{log.AccountID}</td>
                    <td>{log.Balance}</td>
                </tr>
            )
        })
    }

    render() {
        return (
            <div>
                <center>
                <h1>API Log</h1>
                <button className = "w-full py-3 bg-red-600 mt-4 text-white" onClick = {() => {auth.signOut()}}>Sign out</button>
                <table border="1">
                    <tbody>
                        <tr>
                            <th>Date</th>
                            <th>API</th>
                            <th>User ID</th>
                            <th>Account ID</th>
                            <th>Balance</th>
                        </tr>
                        {this.renderTableData()}
                    </tbody>
                </table>
                </center>
            </div>
        )
    }
}

export default AdminLogs;
