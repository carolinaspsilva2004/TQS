// src/components/Modal.js
import React from 'react';
import './Modal.css'; // Arquivo CSS para estilizar o modal

const Modal = ({ message, onConfirm, onCancel }) => {
  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <h2>{message}</h2>
        <div className="modal-actions">
          <button className="modal-btn confirm" onClick={onConfirm}>Confirmar</button>
          <button className="modal-btn cancel" onClick={onCancel}>Cancelar</button>
        </div>
      </div>
    </div>
  );
};

export default Modal;
