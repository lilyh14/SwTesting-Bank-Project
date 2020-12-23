import React, { useContext, useState, useEffect, useCallback } from "react";
import { UserContext } from "../Providers/UserProvider";
import { navigate, Redirect } from "@reach/router";
import {auth, logAPI, updateBalance} from "../firebase";
import { Link } from "@reach/router";
import axios from "axios";

const Dashboard = () => {
  const [creditCardBalance, setCreditCardBalance] = useState("");
  const [creditCardInterestRate, setCreditCardInterestRate] = useState("");
  const [numMonthsToPayOff, setNumMonthsToPayOff] = useState("");

  const [initialDeposit, setInitialDeposit] = useState("");
  const [monthlyContribution, setMonthlyContribution] = useState("");
  const [years, setYears] = useState("");
  const [simpleInterest, setSimpleInterest] = useState("");

  const [mpcreditCardBalance, setMPCreditCardBalance] = useState("");
  const [mpcreditCardInterestRate, setMPCreditCardInterestRate] = useState("");
  const [minPaymentPercentage, setMinPaymentPercentage] = useState("");

  const [deposit, setDeposit] = useState("");
  const [cdyears, setCDYears] = useState("");
  const [apy, setAPY] = useState("");

  const [type, setType] = useState("");
  const [balance, setBalance] = useState("");

  const [accountFrom, setAccountFrom] = useState("");
  const [accountTo, setAccountTo] = useState("");
  const [amount, setAmonut] = useState("");

  const [accountID, setAccountID] = useState("");
  const [depositAmount, setDepositAmount] = useState("");
  const [sourceID, setSourceID] = useState("");

  const createAccountHandler = event => {
    const {name, value} = event.currentTarget;
     if (name === "type") {
      setType(value);
    }else if (name === "balance") {
      setBalance(value);
    }
  }

  const transferFundsHandler = event => {
    const {name, value} = event.currentTarget;
    if(name === "accountFrom"){
      setAccountFrom(value);
    } else if (name === "accountTo") {
      setAccountTo(value);
    }else if (name === "amount") {
      setAmonut(value);
    }
  }

  const depositFundsHandler = event => {
    const {name, value} = event.currentTarget;
    if(name === "accountID"){
      setAccountID(value);
    } else if (name === "depositAmount") {
      setDepositAmount(value);
    }else if (name === "sourceID") {
      setSourceID(value);
    }
  }

  const onCreditCardPayoffChangeHandler = event => {
    const {name, value} = event.currentTarget;
    if (name === "creditCardBalance") {
      setCreditCardBalance(value);
    }
    else if (name === "creditCardInterestRate") {
      setCreditCardInterestRate(value);
    }
    else if (name === "numMonthsToPayOff") {
      setNumMonthsToPayOff(value);
    }
  }

  const onSimpleSavingsChangeHandler = event => {
    const {name, value} = event.currentTarget;
    if (name === "initialDeposit") {
      setInitialDeposit(value);
    }
    else if (name === "monthlyContribution") {
      setMonthlyContribution(value);
    }
    else if (name === "years") {
      setYears(value);
    }
    else if (name === "simpleInterest") {
      setSimpleInterest(value);
    }
  }

  const onMinPaymentChangeHandler = event => {
    const {name, value} = event.currentTarget;
    if (name === "mpcreditCardBalance") {
      setMPCreditCardBalance(value);
    }
    else if (name === "mpcreditCardInterestRate") {
      setMPCreditCardInterestRate(value);
    }
    else if (name === "minPaymentPercentage") {
      setMinPaymentPercentage(value);
    }
  }

  const onCDChangeHandler = event => {
    const {name, value} = event.currentTarget;
    if (name === "deposit") {
      setDeposit(value);
    }
    else if (name === "cdyears") {
      setCDYears(value);
    }
    else if (name === "apy") {
      setAPY(value);
    }
  }

  const user = useContext(UserContext);
  const {fullName, email, netWorth} = user; //API KEY - UPDATE

  /*
  *
  *
  * ------------------BAMS Functions------------------ *
  *
  *
  */
  const getAccounts = async (event) => {
      let axiosConfig = {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${process.env.REACT_APP_BAMS_TOKEN}`
        }
      };

      let accnts = [];
      let nW = 0.00;

      const res = await axios.get(`https://${process.env.REACT_APP_BAMS_API_URL}/v1/accounts/${user.uid}`, axiosConfig).then((res) => {
          res.data.map((accnt) => {
              accnts.push(accnt);
              nW += accnt.balance;
          })
      }).then(() => {
          updateBalance(user, nW);
      })
  }

  //NEED TO ADD NEW FUNDS TO THE OVERALL ON USER ON DB
  //after creating account, need to output accountID
  const createAccount = async(event, _type, _balance) => {
    event.preventDefault();

    let axiosConfig = {
      headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': `Bearer ${process.env.REACT_APP_BAMS_TOKEN}`
      }
    };

    let today = new Date();

    let accID = "";

    const res = await axios.post(`https://${process.env.REACT_APP_BAMS_API_URL}/v1/account`, {
      client_id: user.uid, // AUTOMATICALLY whatever unique email of whoever is logged in
	    type: _type,
	    balance: parseFloat(_balance)
    }, axiosConfig).then((res) => {
        accID = res.data.id;

        logAPI({
            api: "CreateAccount",
            AccountID: res.data.id,
            Balance: parseFloat(res.data.balance),
            Date: ((today.getMonth() + 1) + '/' + today.getDate() + '/' + today.getFullYear()),
            UserID: user.uid
        })
    }).then(() => {
        getAccounts(event);
    }).then(() => {
      console.log("Post successful!")
      alert(
        `Account Successfully Created!\nYour Account ID is ${accID}`
      )
      accID = "";
    })
    .catch(() => {
      console.log("Request failed!")
      alert(
          "Error: Account has not been created."
      )
    })
    //window.location.href = window.location.href;
  }

  const transferFunds = async(event, _accountFrom, _accountTo, _amount) => {
    event.preventDefault();

    let axiosConfig = {
      headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': `Bearer ${process.env.REACT_APP_BAMS_TOKEN}`
      }
    };

    let today = new Date();

   axios.post(`https://${process.env.REACT_APP_BAMS_API_URL}/v1/account/transfer`, {
        from_account: _accountFrom,
        to_account: _accountTo,
        amount: parseFloat(_amount)

    }, axiosConfig).then((res) => {
        logAPI({
            api: "Transfer Funds",
            AccountID: res.data.from_account,
            Balance: parseFloat(res.data.amount_transferred),
            Date: ((today.getMonth() + 1) + '/' + today.getDate() + '/' + today.getFullYear()),
            UserID: user.uid
        })
    }).then(() => {
        getAccounts(event);
    }).then(() => {
      console.log("Post successful!")
      alert(
        "Funds Transferred Successfully"
      )
    })
    .catch(() => {
      console.log("Request failed!")
      alert(
          "Error: Funds have not been transferred."
      )
    })
    //window.location.href = window.location.href;
  }

  const depositFunds = async(event, _accountID, _depositAmount, _sourceID) => {
    event.preventDefault();

    let axiosConfig = {
      headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': `Bearer ${process.env.REACT_APP_BAMS_TOKEN}`
      }
    };

    let today = new Date();

    let newBal = "";

      axios.post(`https://${process.env.REACT_APP_BAMS_API_URL}/v1/account/deposit`, {
        amount: parseFloat(_depositAmount),
        account_id: _accountID,
        source_id: _sourceID
    }, axiosConfig).then((res) => {
        newBal = res.data.new_balance;

        logAPI({
            api: "Deposit Funds",
            AccountID: _accountID,
            Balance: parseFloat(res.data.new_balance),
            Date: ((today.getMonth() + 1) + '/' + today.getDate() + '/' + today.getFullYear()),
            UserID: user.uid
        })
    }).then(() => {
        getAccounts(event);
    }).then(() => {
      console.log("Post successful!")
      alert(
        `Deposit Funds Successfully\nYour new balance is ${newBal}`
      )
      newBal = "";
    })
    .catch(() => {
      console.log("Request failed!")
      alert(
          "Error: Funds have not been deposited."
      )
    })
    //window.location.href = window.location.href;
  }

  /*
  *
  *
  * ------------------CFI Functions------------------ *
  *
  *
  */

  const submitCreditCardPayoff = async (event, _creditCardBalance, _creditCardInterestRate, _numMonthsToPayOff) => {
    event.preventDefault();
    const apiKey = process.env.REACT_APP_CFI_API_KEY;

    if (isNaN(_creditCardBalance) || isNaN(_creditCardInterestRate) || isNaN(_numMonthsToPayOff) || parseInt(_creditCardBalance) < 0 || parseInt(_creditCardInterestRate) < 0 || parseInt(_numMonthsToPayOff) < 0) {
      console.log("bad input");
      alert("Bad input! Request rejected.");
      return;
    }

    const res = await fetch(`http://sw-testing-g3-7ktengsnva-uc.a.run.app/credit-card-payoff?apiKey=${apiKey}`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      },
      body: JSON.stringify({
        creditCardBalance: _creditCardBalance,
        creditCardInterestRate: _creditCardInterestRate,
        numMonthsToPayOff: _numMonthsToPayOff
      })
    }).then(res => res.json())
      .then(data => alert(
        "Monthly Payment: $" + data["monthly-payment"] + "\nTotal Principle Paid: $" + data["total-principle-paid"]
        + "\nTotal Interest Paid: $" + data["total-interest-paid"]
      ))
  }

  const submitSimpleSavings = async (event, _initialDeposit, _monthlyContribution, _years, _simpleInterest) => {
    event.preventDefault();
    const apiKey = process.env.REACT_APP_CFI_API_KEY;

    if (isNaN(_initialDeposit) || isNaN(_monthlyContribution) || isNaN(_years) || isNaN(_simpleInterest) || parseInt(_initialDeposit) < 0 || parseInt(_monthlyContribution) < 0 || parseInt(_years) < 0 || parseInt(_simpleInterest) < 0) {
      console.log("bad input");
      alert("Bad input! Request rejected.");
      return;
    }

    const res = await fetch(`http://sw-testing-g3-7ktengsnva-uc.a.run.app/simple-savings-calculator?apiKey=${apiKey}`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      },
      body: JSON.stringify({
        initialDeposit: _initialDeposit,
        monthlyContribution: _monthlyContribution,
        years: _years,
        simpleInterest: _simpleInterest
      })
    }).then(res => res.json())
      .then(data => alert(
        "Total Savings: $" + data["savings-total"] + "\nTotal Contributions: $" + data["total-contributions"]
        + "\nInterest Earned: $" + data["interest-earned"]
      ))
  }

  const submitMinPaymentCalculator = async (event, _creditCardBalance, _creditCardInterestRate, _minPaymentPercentage) => {
    event.preventDefault();
    const apiKey = process.env.REACT_APP_CFI_API_KEY;

    if (isNaN(_creditCardBalance) || isNaN(_creditCardInterestRate) || isNaN(_minPaymentPercentage) || parseInt(_creditCardBalance) < 0 || parseInt(_creditCardInterestRate) < 0 || parseInt(_minPaymentPercentage) < 0) {
      console.log("bad input");
      alert("Bad input! Request rejected.");
      return;
    }

    const res = await fetch(`http://sw-testing-g3-7ktengsnva-uc.a.run.app/credit-card-min-payment-calculator?apiKey=${apiKey}`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      },
      body: JSON.stringify({
        creditCardBalance: _creditCardBalance,
        creditCardInterestRate: _creditCardInterestRate,
        minPaymentPercentage: _minPaymentPercentage
      })
    }).then(res => res.json())
      .then(data => alert(
        "Monthly Payment: $" + data["monthly-payment"] + "\nNumber of Months: " + data["number-months"]
        + "\nAmount Paid: $" + data["amount-paid"]
      ))
  }

  const submitCDCalculator = async (event, _deposit, _years, _apy) => {
    event.preventDefault();
    const apiKey = process.env.REACT_APP_CFI_API_KEY;

    if (isNaN(_deposit) || isNaN(_years) || isNaN(_apy) || parseInt(_deposit) < 0 || parseInt(_years) < 0 || parseInt(_apy) < 0) {
      console.log("bad input");
      alert("Bad input! Request rejected.");
      return;
    }

    const res = await fetch(`http://sw-testing-g3-7ktengsnva-uc.a.run.app/cd-calculator?apiKey=${apiKey}`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      },
      body: JSON.stringify({
        deposit: _deposit,
        years: _years,
        apy: _apy
      })
    }).then(res => res.json())
      .then(data => alert(
        "Total Balance: $" + data["total-balance"] + "\nTotal Interest Earned: $" + data["total-interest-earned"]
      ))
  }

  const [userAccounts, setUserAccounts] = useState([]);

  const getAccountsForDashboard = useCallback(async () => {
    let axiosConfig = {
      headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': `Bearer ${process.env.REACT_APP_BAMS_TOKEN}`
      }
    };

    let accnts = [];
    let nW = 0.00;

    const res = await axios.get(`https://${process.env.REACT_APP_BAMS_API_URL}/v1/accounts/${user.uid}`, axiosConfig).then((res) => {
        res.data.map((accnt) => {
            console.log(typeof accnt);
            accnts.push(accnt);
            console.log(accnts);
            nW += accnt.balance;
        })
    }).then(() => {
        setUserAccounts(accnts);
    })
  }, [])

  useEffect(() => {
    getAccountsForDashboard();
  }, [getAccountsForDashboard])

  function renderAccounts() {
    return userAccounts.map((account, index) => {
      return (
        <tr key={index}>
          <td>{account.id}</td>
          <td>{account.type}</td>
          <td>{account.balance}</td>
        </tr>
      )
    })
  }

  return (
    <div className = "mx-auto w-11/12 md:w-2/4 py-8 px-4 md:px-8">
      <div className="flex border flex-col items-center md:flex-row md:items-start border-blue-400 px-3 py-4">
        <div className = "md:pl-4">
        <h2 className = "text-2xl font-semibold">Welcome to your dashboard, {fullName}</h2>

        <div>
          {
            user.netWorth == null
            ? <h4>Your overall balance is, {Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(0)}</h4>
            : <h4>Your overall balance is, {Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(user.netWorth)}</h4>
          }
        </div>

        <h4 className = "italic">{email}</h4>
        <button className = "w-full py-3 bg-red-600 mt-4 text-white" id="signout"onClick = {() => {auth.signOut()}}>Sign out</button>
        </div>
      </div>
      <h2 className = "text-2xl font-semibold">Consumer Focused Initiative Functions</h2>

      <h3>Credit Card Payoff:</h3>
      {/* Form for Credit Card Payoff */}
      <form className="credit-card-payoff">
        <label htmlFor="creditCardBalance">
          Credit Card Balance:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="creditCardBalance"
            value={creditCardBalance}
            id="creditCardBalance"
            onChange={event => onCreditCardPayoffChangeHandler(event)}
          />
        <label htmlFor="creditCardInterestRate">
          Credit Card Interest Rate:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="creditCardInterestRate"
            value={creditCardInterestRate}
            id="creditCardInterestRate"
            onChange={event => onCreditCardPayoffChangeHandler(event)}
          />
        <label htmlFor="numMonthsToPayOff">
          Number of Months to Pay Off:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="numMonthsToPayOff"
            value={numMonthsToPayOff}
            id="numMonthsToPayOff"
            onChange={event => onCreditCardPayoffChangeHandler(event)}
          />
        <button
            className="bg-green-400 hover:bg-green-500 w-full py-2 text-white"
            id="CCPCalculate"
            onClick={event => {
              submitCreditCardPayoff(event, creditCardBalance, creditCardInterestRate, numMonthsToPayOff);
            }}
          >
            Calculate
          </button>
      </form>

      <h3>Simple Savings Calculator:</h3>
      {/* form for simple savings calculator */}
      <form className="simple-savings-calculator">
        <label htmlFor="initialDeposit">
          Initial Deposit:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="initialDeposit"
            value={initialDeposit}
            id="initialDeposit"
            onChange={event => onSimpleSavingsChangeHandler(event)}
          />
        <label htmlFor="monthlyContribution">
          Monthly Contribution:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="monthlyContribution"
            value={monthlyContribution}
            id="monthlyContribution"
            onChange={event => onSimpleSavingsChangeHandler(event)}
          />
        <label htmlFor="years">
          Years:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="years"
            value={years}
            id="years"
            onChange={event => onSimpleSavingsChangeHandler(event)}
          />
        <label htmlFor="simpleInterest">
          Simple Interest:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="simpleInterest"
            value={simpleInterest}
            id="simpleInterest"
            onChange={event => onSimpleSavingsChangeHandler(event)}
          />
        <button
            className="bg-green-400 hover:bg-green-500 w-full py-2 text-white"
            id="SSCalculate"
            onClick={event => {
              submitSimpleSavings(event, initialDeposit, monthlyContribution, years, simpleInterest);
            }}
          >
            Calculate
          </button>
      </form>

      <h3>Credit Card Minimum Payment Calculator:</h3>
      {/* form for Minimum Payment Calculator */}
      <form className="credit-card-min-payment-calculator">
        <label htmlFor="mpcreditCardBalance">
          Credit Card Balance:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="mpcreditCardBalance"
            value={mpcreditCardBalance}
            id="mpcreditCardBalance"
            onChange={event => onMinPaymentChangeHandler(event)}
          />
        <label htmlFor="mpcreditCardInterestRate">
          Credit Card Interest Rate:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="mpcreditCardInterestRate"
            value={mpcreditCardInterestRate}
            id="mpcreditCardInterestRate"
            onChange={event => onMinPaymentChangeHandler(event)}
          />
        <label htmlFor="minPaymentPercentage">
          Minimum Payment Percentage:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="minPaymentPercentage"
            value={minPaymentPercentage}
            id="minPaymentPercentage"
            onChange={event => onMinPaymentChangeHandler(event)}
          />
        <button
            className="bg-green-400 hover:bg-green-500 w-full py-2 text-white"
            onClick={event => {
              submitMinPaymentCalculator(event, mpcreditCardBalance, mpcreditCardInterestRate, minPaymentPercentage);
            }}
          >
            Calculate
          </button>
      </form>

      <h3>CD Calculator:</h3>
      {/* form for CD Calculator */}
      <form className="cd-calculator">
        <label htmlFor="deposit">
          Deposit:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="deposit"
            value={deposit}
            id="deposit"
            onChange={event => onCDChangeHandler(event)}
          />
        <label htmlFor="cdyears">
          Years:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="cdyears"
            value={cdyears}
            id="cdyears"
            onChange={event => onCDChangeHandler(event)}
          />
        <label htmlFor="apy">
          APY:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="apy"
            value={apy}
            id="apy"
            onChange={event => onCDChangeHandler(event)}
          />
        <button
            className="bg-green-400 hover:bg-green-500 w-full py-2 text-white"
            onClick={event => {
              submitCDCalculator(event, deposit, cdyears, apy);
            }}
          >
            Calculate
          </button>
      </form>
      <h2 className = "text-2xl font-semibold">Bank Account Management System</h2>
      <h3>Create Account</h3>
      <form className="createAccount">
        <label htmlFor="type">
          Type:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="type"
            value={type}
            id="type"
            onChange={event => createAccountHandler(event)}
          />
        <label htmlFor="balance">
          Balance:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="balance"
            value={balance}
            id="balance"
            onChange={event => createAccountHandler(event)}
          />
        <button
            className="bg-green-400 hover:bg-green-500 w-full py-2 text-white"
            onClick={event => {
              createAccount(event, type, balance);
            }}
          >
            Submit
          </button>

      </form>

      <h3>Transfer Funds</h3>
      <form className="transferFunds">
      <label htmlFor="accountFrom">
            Account From:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="accountFrom"
            value={accountFrom}
            id="accountFrom"
            onChange={event => transferFundsHandler(event)}
          />
        <label htmlFor="accountTo">
          Account To:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="accountTo"
            value={accountTo}
            id="accountTo"
            onChange={event =>transferFundsHandler(event)}
          />
        <label htmlFor="amount">
          Amount:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="amount"
            value={amount}
            id="amount"
            onChange={event => transferFundsHandler(event)}
          />
        <button
            className="bg-green-400 hover:bg-green-500 w-full py-2 text-white"
            id="transferSubmit"
            onClick={event => {
              transferFunds(event, accountFrom, accountTo, amount);
            }}
          >
            Submit
          </button>

      </form>

      <h3>Deposit Funds</h3>
      <form className="depositFunds">
      <label htmlFor="accountID">
            Account ID:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="accountID"
            value={accountID}
            id="accountID"
            onChange={event => depositFundsHandler(event)}
          />
        <label htmlFor="depositAmount">
          Amount:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="depositAmount"
            value={depositAmount}
            id="depositAmount"
            onChange={event =>depositFundsHandler(event)}
          />
        <label htmlFor="sourceID">
          SourceID:
        </label>
        <input
            type="text"
            className="my-1 p-1 w-full "
            name="sourceID"
            value={sourceID}
            id="sourceID"
            onChange={event => depositFundsHandler(event)}
          />
        <button
            className="bg-green-400 hover:bg-green-500 w-full py-2 text-white"
            id="depositSubmit"
            onClick={event => {
              depositFunds(event, accountID, depositAmount, sourceID);
            }}
          >
            Submit
          </button>

      </form>

      <div>
        <br></br>
        <table border="1">
          <tbody>
            <tr>
              <th>Account Number</th>
              <th>Type</th>
              <th>Balance</th>
            </tr>
            {renderAccounts()}
          </tbody>
        </table>
      </div>

    </div>
  )
};
export default Dashboard;
