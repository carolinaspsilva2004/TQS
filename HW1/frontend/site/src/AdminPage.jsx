// src/components/AdminPage.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './AdminPage.css'; // Importação do arquivo CSS para o estilo

const AdminPage = () => {
  const [reservations, setReservations] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Buscar todas as reservas do backend
    axios.get('http://localhost:8080/reservations')
      .then(response => {
        setReservations(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error('Erro ao buscar reservas:', error);
        setLoading(false);
      });
  }, []);

  const handleVerifyReservation = (code) => {
    // Enviar a requisição para marcar a reserva como usada
    axios.post(`http://localhost:8080/reservations/checkin/${code}`)
      .then(response => {
        // Atualizar o estado após a marcação de verificação
        setReservations(prevReservations => 
          prevReservations.map(reservation => 
            reservation.code === code ? { ...reservation, used: true } : reservation
          )
        );
      })
      .catch(error => {
        console.error('Erro ao verificar a reserva:', error);
        alert('Erro ao verificar a reserva.');
      });
  };

  return (
    <div className="admin-page">
      <h1>Painel de Admin - Verificação de Reservas</h1>
      {loading ? (
        <div className="loading">Carregando reservas...</div>
      ) : (
        <div className="reservations-container">
          {reservations.length === 0 ? (
            <p className="no-reservations">Não há reservas disponíveis.</p>
          ) : (
            <ul className="reservations-list">
              {reservations.map(reservation => (
                <li key={reservation.code} className={`reservation-item ${reservation.used ? 'used' : 'pending'}`}>
                  <div className="reservation-details">
                    <p><strong>Restaurante:</strong> {reservation.meal?.restaurant?.name}</p>
                    <p><strong>Data da Refeição:</strong> {reservation.meal?.date}</p>
                    <p><strong>Código:</strong> {reservation.code}</p>
                    <p><strong>Status:</strong> {reservation.used ? 'Usada' : 'Pendente'}</p>
                  </div>
                  {!reservation.used && (
                    <button 
                      className="verify-btn"
                      onClick={() => handleVerifyReservation(reservation.code)}
                    >
                      Marcar como Usada
                    </button>
                  )}
                </li>
              ))}
            </ul>
          )}
        </div>
      )}
    </div>
  );
};

export default AdminPage;
