// src/components/SuccessModal.js
import React from 'react';
import './SuccessModal.css';

const SuccessModal = ({ message, onClose }) => {
  return (
    <div className="success-modal-overlay">
      <div className="success-modal-content">
        <button className="close-btn" onClick={onClose}>✖</button>
        <h2>{message}</h2>
      </div>
    </div>
  );
};

export default SuccessModal;
