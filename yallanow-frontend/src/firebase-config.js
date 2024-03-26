// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getAuth, createUserWithEmailAndPassword, signInWithEmailAndPassword , getIdToken, logout, sendPasswordResetEmail} from "firebase/auth";
import{getDatabase, ref, set} from "firebase/database";

const firebaseConfig = {
  apiKey: "AIzaSyAFU_zSpZWKznKiEiNqrNwsi-8JIr5XfhQ",
  authDomain: "yallanow12.firebaseapp.com",
  projectId: "yallanow12",
  storageBucket: "yallanow12.appspot.com",
  messagingSenderId: "463351798443",
  appId: "1:463351798443:web:dc83ad7b3ebbf1e88f75ee",
  measurementId: "G-77DNY9TMVN"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const auth = getAuth();
const database = getDatabase();


const Register = async (signup) => {
  console.log("we are in register");
  const { email, password } = signup;

    const userCredential = await createUserWithEmailAndPassword(auth, email, password)
    .then((userCredential) => {
      // Signed in 
      console.log(userCredential);
      const user = userCredential.user;
      // ...
    })
    .catch((error) => {
      const errorCode = error.code;
      const errorMessage = error.message;
      // ..
    });

    console.log("we are exiting register")
}


const Login = async (login) => {
  console.log("we are in login");
  const { email, password } = login;
  await signInWithEmailAndPassword(auth, email, password)
  .then((userCredential) => {
    // Signed in 
    console.log(userCredential)
    const user = userCredential.user;
    const idToken =  getIdToken(user);
    console.log(idToken);
    // ...
  })
  .catch((error) => {
    const errorCode = error.code;
    const errorMessage = error.message;
  });
  console.log("we are exiting login");
}


const logout = async () => {
  console.log("we are in logout");
  await signOut(auth).then(() => {
    console.log("we are exiting logout");
  }).catch((error) => {
    console.log(error);
  });
}

const resetPassword = async (email) => {
  console.log("we are in reset password");
  await sendPasswordResetEmail(auth, email)
  .then(() => {
    console.log("we are exiting reset password");
  }).catch((error) => {
    console.log(error);
  });
}




export {Register, Login, logout, resetPassword};
// export const googleProvider = new GoogleAuthProvider();

