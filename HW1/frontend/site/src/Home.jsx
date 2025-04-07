import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FaUtensils, FaCalendarAlt, FaCheckCircle } from 'react-icons/fa';
import './App.css';
import Tempo from './Tempo';
import { useNavigate } from 'react-router-dom';
import Modal from './Modal';
import SuccessModal from './SuccessModal'; 
import logoEmentas from './logo_ementas.svg';


const Home = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [selectedRestaurant, setSelectedRestaurant] = useState(null);
  const [restaurantDate, setRestaurantDate] = useState('');
  const [meals, setMeals] = useState([]);
  const [reservationCode, setReservationCode] = useState('');
  const [showModal, setShowModal] = useState(false); 
  const [showSuccessModal, setShowSuccessModal] = useState(false); 
  const [mealIdToBook, setMealIdToBook] = useState(null); 
  const [restaurantDateError, setRestaurantDateError] = useState('');


  const navigate = useNavigate();

  useEffect(() => {
    axios.get('http://localhost:8080/restaurants')
      .then(response => setRestaurants(response.data))
      .catch(error => console.error('Erro ao carregar restaurantes:', error));
  }, []);

  const fetchMeals = () => {
    if (selectedRestaurant && restaurantDate) {
      axios.get(`http://localhost:8080/meals/restaurant/${selectedRestaurant.id}`)
        .then(response => {
          const filteredMeals = response.data.filter(meal => meal.date === restaurantDate);
          setMeals(filteredMeals);
        })
        .catch(error => console.error('Erro ao carregar ementas:', error));
    }
  };

  const generateCode = () => {
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    const length = Math.floor(Math.random() * 3) + 4; // 4 a 6
    return Array.from({ length }, () => chars[Math.floor(Math.random() * chars.length)]).join('');
  };

  const bookReservation = (mealId) => {
    // Mostra o modal para confirmação
    setMealIdToBook(mealId);
    setShowModal(true);
  };

  const confirmReservation = () => {
    const code = generateCode();
    axios.post(`http://localhost:8080/reservations/book/${mealIdToBook}`, { code })
      .then(response => {
        setReservationCode(code);
        setShowModal(false); // Fecha o modal após confirmar a reserva
        setShowSuccessModal(true); // Exibe o SuccessModal
      })
      .catch(error => {
        console.error('Erro ao fazer a reserva:', error);
        alert('Erro ao tentar fazer a reserva.');
        setShowModal(false); // Fecha o modal em caso de erro
      });
  };

  const cancelReservation = () => {
    setShowModal(false); // Fecha o modal sem fazer a reserva
  };

  const closeSuccessModal = () => {
    setShowSuccessModal(false); // Fecha o SuccessModal após o usuário fechar
  };

  // Função formatDescription corrigida
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
    <>
      <div>
        <h1 className="main-title">Reserva a tua Refeição consoante o Clima</h1>
        <img src={logoEmentas} alt="Reserva" className="reserva-img" />
        <p className="main-description">Escolhe o restaurante e a data da ementa, e depois escolhe a cidade e a data do clima.</p>
      </div>
      <div className="reserva-container">
      <button onClick={() => navigate('/reservas')} className="minhas-reservas-btn">
        <FaUtensils className="icon-minhas-reservas" />
        As Minhas Reservas
      </button> 
    </div>

      <div className="home">
        {/* Lado esquerdo: restaurantes */}
        <div className="left-side">
          <h2 className="section-title">Reserva a tua Refeição</h2>
          <p>Escolhe o restaurante e a data da ementa</p>
          <div className="form-group">
            <label htmlFor="restaurant">
              <FaUtensils className="icon" />
              Restaurante:
            </label>
            <select id="restaurant" onChange={(e) => {
              const selected = restaurants.find(r => r.id === Number(e.target.value));
              setSelectedRestaurant(selected);
            }}>
              <option value="">Selecione um restaurante</option>
              {restaurants.map((restaurant) => (
                <option key={restaurant.id} value={restaurant.id}>{restaurant.name}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="restaurantDate">
              <FaCalendarAlt className="icon" />
              Data (ementa):
            </label>
            <input
        type="date"
        id="restaurantDate"
        value={restaurantDate}
        onChange={(e) => {
          const selectedDate = e.target.value;
          setRestaurantDate(selectedDate);

          const today = new Date();
          const inputDate = new Date(selectedDate + 'T00:00:00'); // Evita problemas de fuso horário

          if (inputDate < today.setHours(0, 0, 0, 0)) {
            setRestaurantDateError('A data não pode ser anterior a hoje.');
          } else {
            setRestaurantDateError('');
          }
        }}
      />
      {restaurantDateError && (
        <p style={{ color: 'red', fontSize: '0.9rem', marginTop: '5px' }}>{restaurantDateError}</p>
      )}
          </div>

          <button onClick={fetchMeals} className="confirm-btn">
            <FaCheckCircle className="icon" />
            Confirmar Restaurante/Data
          </button>

          <div className="menu-section">
            <h3 className="section-subtitle">Ementa</h3>
            <ul>
              {meals.length > 0 ? (
                meals.map((meal) => (
                  <li key={meal.id} className="meal-card">
                    <h4>{meal.name}</h4>
                    <p>{formatDescription(meal.description)}</p>
                    <button className="reservar-btn" onClick={() => bookReservation(meal.id)}>Reservar</button>
                  </li>
                ))
              ) : (
                <p className="no-meals-text">Selecione um restaurante e uma data e clique em "Confirmar" para ver a ementa.</p>
              )}
            </ul>
          </div>

          {reservationCode && (
            <div className="reservation-section">
              <h3>Código da Reserva: {reservationCode}</h3>
            </div>
          )}
        </div>

        {/* Lado direito: clima */}
        <div className="right-side">
          <Tempo />
        </div>
      </div>

      {showModal && (
        <Modal
          message={`Tem a certeza que pretende realizar a reserva para o dia ${restaurantDate}?`}
          onConfirm={confirmReservation}
          onCancel={cancelReservation}
        />
      )}

      {showSuccessModal && (
        <SuccessModal
          message={`Reserva realizada com sucesso! Código de reserva: ${reservationCode}`}
          onClose={closeSuccessModal}
        />
      )}
    </>
  );
};

export default Home;
