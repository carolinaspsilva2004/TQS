import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FaUtensils, FaCalendarAlt, FaCheckCircle } from 'react-icons/fa';
import './App.css';
import Tempo from './Tempo';

const Home = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [selectedRestaurant, setSelectedRestaurant] = useState(null);
  const [restaurantDate, setRestaurantDate] = useState('');
  const [meals, setMeals] = useState([]);
  const [reservationCode, setReservationCode] = useState('');  // Armazena o código da reserva

  useEffect(() => {
    // Carrega os restaurantes ao iniciar a página
    axios.get('http://localhost:8080/restaurants')
      .then(response => setRestaurants(response.data))
      .catch(error => console.error('Erro ao carregar restaurantes:', error));
  }, []);

  const fetchMeals = () => {
    if (selectedRestaurant && restaurantDate) {
      // Carrega as refeições do restaurante para a data selecionada
      axios.get(`http://localhost:8080/meals/restaurant/${selectedRestaurant.id}`)
        .then(response => {
          const filteredMeals = response.data.filter(meal => meal.date === restaurantDate);
          setMeals(filteredMeals);
        })
        .catch(error => console.error('Erro ao carregar ementas:', error));
    }
  };

  // Função para reservar a refeição
  const bookReservation = (mealId) => {
    axios.post(`http://localhost:8080/reservations/book/${mealId}`)
      .then(response => {
        const reservation = response.data;
        alert(`Reserva feita com sucesso! Código de reserva: ${reservation.code}`);
        setReservationCode(reservation.code);
      })
      .catch(error => {
        console.error('Erro ao fazer a reserva:', error);
        alert('Erro ao tentar fazer a reserva.');
      });
  };

  return (
    <>
    <div>
        <h1 className="main-title">Reserva a tua Refeição consoante o Clima</h1>
        <p className="main-description">Escolhe o restaurante e a data da ementa, e depois escolhe a cidade e a data do clima.</p>
    </div>
    <div className="home">
      {/* Lado esquerdo: restaurantes */}
      <div className="left-side">
        <h2 className="section-title">Selecione o Restaurante e Data</h2>
        
        {/* Formulário para selecionar restaurante e data */}
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
          <input type="date" id="restaurantDate" value={restaurantDate} onChange={(e) => setRestaurantDate(e.target.value)} />
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
                  <p>{meal.description}</p>
                  <button onClick={() => bookReservation(meal.id)}>Reservar</button>
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
    </>
  );
};

export default Home;
