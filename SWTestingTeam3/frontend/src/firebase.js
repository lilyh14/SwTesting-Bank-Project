import firebase from "firebase/app";
import "firebase/auth";
import "firebase/firestore";

const firebaseConfig = {
    apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
    authDomain: process.env.REACT_APP_FIREBASE_AUTH_DOMAIN,
    databaseURL: process.env.REACT_APP_FIREBASE_DATABASE_URL,
    projectId: process.env.REACT_APP_FIREBASE_PROJECT_ID,
    storageBucket: process.env.REACT_APP_FIREBASE_STORAGE_BUCKET,
    messagingSenderId: process.env.REACT_APP_FIREBASE_MESSAGING_SENDER_ID,
    appId: process.env.REACT_APP_FIREBASE_APP_ID,
    measurementId: process.env.REACT_APP_FIREBASE_MEASUREMENT_ID
  };
  if (!firebase.apps.length) {
    firebase.initializeApp(firebaseConfig);
 }

export const auth = firebase.auth();
export const firestore = firebase.firestore();

export const generateUserDocument = async (user, additionalData) => {
    let netWorth = 0;

    if (!user) return;
    const userRef = firestore.doc(`users/${user.uid}`);
    const snapshot = await userRef.get();
    if (!snapshot.exists) {
      const { email, fullName } = user;
      try {
        await userRef.set({
          fullName,
          email,
          netWorth,
          ...additionalData
        });
      } catch (error) {
        console.error("Error creating user document", error);
      }
    }
    return getUserDocument(user.uid);
  };
  const getUserDocument = async uid => {
    if (!uid) return null;
    try {
      const userDocument = await firestore.doc(`users/${uid}`).get();
      return {
        uid,
        ...userDocument.data()
      };
    } catch (error) {
      console.error("Error fetching user", error);
    }
  };
  export const logAPI = async (apiCall) => {
      const confirmation_number = Math.random().toString(36).substring(2,12);

      const ref = firestore.doc(`API Calls/${confirmation_number}`);
      const snapshot = await ref.get();
        const { api, AccountID, Balance, Date, UserID } = apiCall;
        try {
          await ref.set({
            api,
            AccountID,
            Balance,
            Date,
            UserID
          });
        } catch (error) {
          console.error("Error creating user document", error);
        }
    };

    export const updateBalance = async (user, bal) => {
        await firestore.doc(`users/${user.uid}`).update({netWorth: bal});
    };
