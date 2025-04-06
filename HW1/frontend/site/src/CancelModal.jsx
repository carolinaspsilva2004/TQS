// src/components/CancelModal.js
import React from 'react';
import './CancelModal.css'; // Arquivo CSS para estilizar o modal

const CancelModal = ({ message, onConfirm, onCancel }) => {
  return (
    <div className="cancel-modal-overlay">
      <div className="cancel-modal-content">
        <button className="close-btn" onClick={onCancel}>✖</button>
        <h2>{message}</h2>
        <div className="modal-buttons">
          <button className="confirm-btn" onClick={onConfirm}>Sim</button>
          <button className="cancel-btn" onClick={onCancel}>Não</button>
        </div>
      </div>
    </div>
  );
};

export default CancelModal;
