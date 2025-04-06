// src/components/MyReservations.js
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './MyReservations.css';
import { FaArrowLeft } from 'react-icons/fa';
import CancelModal from './CancelModal'; // Importando o modal de cancelamento
import SuccessModal from './SuccessModal'; // Importando o modal de sucesso

const MyReservations = () => {
  const [reservations, setReservations] = useState([]);
  const [showCancelModal, setShowCancelModal] = useState(false);
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [cancelCode, setCancelCode] = useState(null);
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    axios.get('http://localhost:8080/reservations')
      .then(response => setReservations(response.data))
      .catch(error => console.error('Erro ao buscar reservas:', error));
  }, []);

  const cancelReservation = (code) => {
    setCancelCode(code);
    setMessage('Tem a certeza que deseja cancelar esta reserva?');
    setShowCancelModal(true);
  };

  const confirmCancellation = () => {
    axios.delete(`http://localhost:8080/reservations/${cancelCode}`)
      .then(() => {
        setReservations(prev => prev.filter(r => r.code !== cancelCode));
        setShowCancelModal(false);
        setMessage('Reserva cancelada com sucesso.');
        setShowSuccessModal(true);
      })
      .catch(() => {
        setShowCancelModal(false);
        setMessage('Erro ao cancelar reserva.');
        setShowSuccessModal(true);
      });
  };

  const cancelModalClose = () => {
    setShowCancelModal(false); // Fecha o modal de cancelamento sem cancelar a reserva
  };

  const closeSuccessModal = () => {
    setShowSuccessModal(false); // Fecha o modal de sucesso
  };

  const formatDescription = (desc) => {
    if (!desc) return '';

    const parts = desc.split(';');
    const firstPart = parts[0].trim();
    const rest = parts.slice(1);

    let formattedFirstPart;

    if (firstPart.startsWith('Almoço') || firstPart.startsWith('Jantar')) {
      const [firstWord, ...after] = firstPart.split(' ');
      const remaining = after.join(' ').replace(/^->\s*/, '').trim();

      formattedFirstPart = (
        <>
          <strong>{firstWord}</strong><br />
          {remaining && (
            <>
              {remaining}
              <br />
            </>
          )}
        </>
      );
    } else {
      formattedFirstPart = (
        <>
          {firstPart}
          <br />
        </>
      );
    }

    const restFormatted = rest.map((item, i) => (
      <React.Fragment key={i}>
        {item.trim()}
        <br />
      </React.Fragment>
    ));

    return (
      <>
        {formattedFirstPart}
        {restFormatted}
      </>
    );
  };

  return (
    <div className="reservations-page">
      <button 
        className="back-button" 
        onClick={() => navigate('/home')}
      >
        <FaArrowLeft className="back-button-icon" />
        <span className="back-button-text">Voltar</span>
      </button>

      <h1>As Minhas Reservas</h1>
      {reservations.length > 0 ? (
        <ul className="reservations-list">
          {reservations.map(res => (
            <li key={res.id} className="reservation-card">
              <p><strong>Restaurante:</strong> {res.meal?.restaurant?.name}</p>
              <p><strong>Data da Refeição:</strong> {res.meal?.date}</p>
              <p><strong>Ementa:</strong> {formatDescription(res.meal?.description)}</p>
              <p><strong>Código:</strong> {res.code?.substring(0, 6).toUpperCase()}</p>
              <button className="cancel-btn" onClick={() => cancelReservation(res.code)}>
                Cancelar
              </button>
            </li>
          ))}
        </ul>
      ) : (
        <p>Não há reservas ativas.</p>
      )}

      {showCancelModal && (
        <CancelModal
          message={message}
          onConfirm={confirmCancellation}
          onCancel={cancelModalClose}
        />
      )}

      {showSuccessModal && (
        <SuccessModal
          message={message}
          onClose={closeSuccessModal}
        />
      )}
    </div>
  );
};

export default MyReservations;
