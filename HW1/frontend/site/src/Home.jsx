import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FaCloudSun, FaCloudRain, FaSun, FaCloud } from 'react-icons/fa';
import { FaUtensils, FaCalendarAlt, FaCheckCircle } from 'react-icons/fa';
import { FaSearchLocation, FaCalendarDay } from 'react-icons/fa';
import './App.css';

const Home = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [selectedRestaurant, setSelectedRestaurant] = useState(null);
  const [restaurantDate, setRestaurantDate] = useState('');
  const [weatherDate, setWeatherDate] = useState('');
  const [meals, setMeals] = useState([]);
  const [weather, setWeather] = useState([]);
  const [weatherSummary, setWeatherSummary] = useState(null);
  const [city, setCity] = useState('');

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

  const fetchWeather = () => {
    if (city && weatherDate) {
      axios.get(`http://localhost:8080/weather/${city}/${weatherDate}`)
        .then(response => {
          console.log("Resposta da API:", response.data); // Verifica a estrutura da resposta
          // Normaliza a data para comparar corretamente
          const dayData = response.data.days.find(day => day.datetime === weatherDate);
          if (dayData) {
            setWeather(dayData.hours);  // Definir as horas para o estado
            setWeatherSummary({
              temp: dayData.temp,
              conditions: dayData.conditions
            });
          } else {
            setWeather([]);
            setWeatherSummary(null);
          }
        })
        .catch(error => {
          console.error('Erro ao carregar o clima:', error);
          setWeather([]);
          setWeatherSummary(null);
        });
    }
  };
  

  const getWeatherIcon = (condition) => {
    if (condition.includes('Rain')) {
      return <FaCloudRain size={40} color="#1e90ff" />;
    } else if (condition.includes('Clear')) {
      return <FaSun size={40} color="#ffcc00" />;
    } else if (condition.includes('Partially cloudy')) {
      return <FaCloudSun size={40} color="#f4a300" />;
    } else {
      return <FaCloud size={40} color="#808080" />;
    }
  };

  return (
    <div className="home" style={{ display: 'flex', gap: '2rem' }}>
      {/* Lado esquerdo: restaurantes */}
      <div className="left-side">
        <h2 className="section-title">Selecione o Restaurante e Data</h2>

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
                </li>
              ))
            ) : (
              <p className="no-meals-text">Selecione um restaurante e uma data e clique em "Confirmar" para ver a ementa.</p>
            )}
          </ul>
        </div>
      </div>

      {/* Lado direito: clima */}
      <div className="right-side">
        <h2 className="section-title">Previsão do Tempo</h2>

        <div className="form-group">
          <label htmlFor="city">
            <FaSearchLocation className="icon" />
            Cidade:
          </label>
          <input 
            type="text" 
            id="city" 
            value={city} 
            onChange={(e) => setCity(e.target.value)} 
            placeholder="Digite o nome da cidade"
          />
        </div>

        <div className="form-group">
          <label htmlFor="weatherDate">
            <FaCalendarDay className="icon" />
            Data (clima):
          </label>
          <input 
            type="date" 
            id="weatherDate" 
            value={weatherDate} 
            onChange={(e) => setWeatherDate(e.target.value)} 
          />
        </div>

        <button onClick={fetchWeather} className="confirm-btn">
          <FaCloudSun className="icon" />
          Confirmar Cidade/Data
        </button>

        {weatherSummary && (
  <div className="weather-results">
    <h3 className="section-subtitle">Previsão para {city} em {weatherDate}</h3>

    {/* Card do resumo diário com a classe específica */}
    <div className="hourly-summary-card">
      <h4>Resumo Diário</h4>
      <div className="hourly-info">
        {getWeatherIcon(weatherSummary.conditions)}
        <div>
          <p><strong>Temperatura média:</strong> {weatherSummary.temp}°C</p>
          <p><strong>Condições gerais:</strong> {weatherSummary.conditions}</p>
        </div>
      </div>
    </div>
  </div>
)}

{weather.length > 0 && (
  <div className="weather-results">
    <h3 className="section-subtitle">Previsão por hora</h3>
    <div className="hourly-scroll">
      {weather.map((hour, index) => (
        <div key={index} className="hourly-card">
          <h4>{hour.datetime.substring(0, 5)}h</h4>
          <div className="hourly-info">
            {getWeatherIcon(hour.conditions)}
            <div>
              <p><strong>Temperatura:</strong> {hour.temp}°C</p>
              <p><strong>Condições:</strong> {hour.conditions}</p>
            </div>
          </div>
        </div>
      ))}
    </div>
  </div>
)}

      </div>
    </div>
  );
};

export default Home;
