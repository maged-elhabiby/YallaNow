import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import {
  getAuth,
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword,
  getIdToken,
  signOut,
  sendPasswordResetEmail,
  onAuthStateChanged,
  updateProfile,
  GoogleAuthProvider,
  signInWithPopup,
  setPersistence,
  browserLocalPersistence,
} from "firebase/auth";
import { getDatabase } from "firebase/database";

const firebaseConfig = {
  apiKey: "AIzaSyAFU_zSpZWKznKiEiNqrNwsi-8JIr5XfhQ",
  authDomain: "yallanow12.firebaseapp.com",
  projectId: "yallanow12",
  storageBucket: "yallanow12.appspot.com",
  messagingSenderId: "463351798443",
  appId: "1:463351798443:web:dc83ad7b3ebbf1e88f75ee",
  measurementId: "G-77DNY9TMVN",
};

const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const auth = getAuth();
const database = getDatabase();
const provider = new GoogleAuthProvider();

onAuthStateChanged(auth, (user) => {
  if (user) {
    console.log("User is signed in");
    localStorage.setItem("SignedIn", true);
  } else {
    console.log("User is signed out");
    localStorage.setItem("SignedIn", false);
  }
});

const googleSignIn = async () => {
  try {
    const result = await signInWithPopup(auth, provider);
    const user = result.user;
    const idToken = await getIdToken(user);
    localStorage.setItem("idToken", idToken);
    return true;
  } catch (error) {
    console.error("Error during Google sign-in:", error);
    return false;
  }
};

const register = async ({ email, password, name }) => {
  try {
    await setPersistence(auth, browserLocalPersistence);
    const userCredential = await createUserWithEmailAndPassword(auth, email, password);
    await updateProfile(userCredential.user, { displayName: name });
    const idToken = await getIdToken(userCredential.user);
    localStorage.setItem("idToken", idToken);
    return true;
  } catch (error) {
    console.error("Error during registration:", error);
    return false;
  }
};

const login = async ({ email, password }) => {
  try {
    await setPersistence(auth, browserLocalPersistence);
    const userCredential = await signInWithEmailAndPassword(auth, email, password);
    const idToken = await getIdToken(userCredential.user);
    localStorage.setItem("idToken", idToken);
    return true;
  } catch (error) {
    console.error("Error during login:", error);
    return false;
  }
};

const logoutFirebase = async () => {
  try {
    await signOut(auth);
    localStorage.removeItem("idToken");
  } catch (error) {
    console.error("Error during logout:", error);
  }
};

const resetPassword = async (email) => {
  try {
    await sendPasswordResetEmail(auth, email);
    return true;
  } catch (error) {
    console.error("Error during password reset:", error);
    return false;
  }
};

export { register, login, logoutFirebase, resetPassword, auth, googleSignIn };
