import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './Home';
import MyReservations from './MyReservations';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/home" element={<Home />} />
        <Route path="/reservas" element={<MyReservations />} />
      </Routes>
    </Router>
  );
}

export default App;
