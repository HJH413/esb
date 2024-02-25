import React from 'react';
import Layout from "../layout/Layout";
import "../assets/css/common.scss";
import "../assets/css/login.scss";
import LoginForm from "../component/Login/LoginForm";

const Login = () => {
    return (
        <Layout>
            <LoginForm/>
        </Layout>
    )
}

export default Login;