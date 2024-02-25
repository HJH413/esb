import React from 'react';
import {
    Routes,
    Route
} from "react-router-dom";
import Login from "./pages/Login";
import TimeCheck from "./pages/TimeCheck";

function App() {
    return (
        <Routes>
            <Route path="/" element={<Login/>}/>
            <Route path="/timeCheck" element={<TimeCheck/>}/>
        </Routes>
    );
}

export default App;
