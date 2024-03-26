// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getAuth, createUserWithEmailAndPassword, signInWithEmailAndPassword , getIdToken, signOut, sendPasswordResetEmail, onAuthStateChanged, revokeAccessToken, updateProfile} from "firebase/auth";
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
var currentuser = null;



onAuthStateChanged(auth, (user) => {
  if (user) {
    console.log("User is signed in");
    console.log(user);
    localStorage.setItem("SignedIn", true);
    currentuser = user;
  } else {
    console.log("User is signed out");
    localStorage.setItem("SignedIn", false);
    currentuser = null;

  }
});

const Register = async (signup) => {
  console.log("we are in register");
  const { email, password, name } = signup;

    const response = await createUserWithEmailAndPassword(auth, email, password)
    .then(async (userCredential) => {


      
      // Signed in 

      console.log(userCredential);

      var user = userCredential.user;
      await updateProfile(user, {
        displayName: name

      });
  
      const idToken =  await getIdToken(user);
      localStorage.setItem("idToken", idToken);
      // ...
    })
    .catch((error) => {
      const errorCode = error.code;
      const errorMessage = error.message;
      return false;

      // ..
    });
    if (response === false) {
      return false;
    }


    


    



    console.log("we are exiting register")
    return true;
}


const Login = async (login) => {

  console.log("we are in login");
  const { email, password } = login;
  console.log(email + " +" + password);
  const response = await signInWithEmailAndPassword(auth, email, password)
  .then(async (userCredential) => {

    console.log(userCredential)
    const user = userCredential.user;
    const idToken =  await getIdToken(user);
    localStorage.setItem("idToken", idToken);

  }

).catch((error) => {
    const errorCode = error.code;
    const errorMessage = error.message;
    return false;
  });

  console.log(response);
  if (response === false) {
    return false;
  }

  console.log("we are exiting login");
  return true;
}


const logoutfirebase = async () => {
  console.log("we are in logout");
  await signOut(auth).then(() => {
    console.log("we are exiting logout");
    localStorage.removeItem("idToken");
  
  currentuser = null;


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
    return false;
  });
  return true;
}




export {Register, Login, logoutfirebase, resetPassword};
// export const googleProvider = new GoogleAuthProvider();

