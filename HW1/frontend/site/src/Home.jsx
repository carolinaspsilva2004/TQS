import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FaCloudSun, FaCloudRain, FaSun, FaCloud } from 'react-icons/fa';

const Home = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [selectedRestaurant, setSelectedRestaurant] = useState(null);
  const [restaurantDate, setRestaurantDate] = useState('');
  const [weatherDate, setWeatherDate] = useState('');
  const [meals, setMeals] = useState([]);
  const [weather, setWeather] = useState(null);
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
      axios.get(`http://localhost:8080/weather/${city}/${weatherDate}/hours`)
        .then(response => setWeather(response.data))
        .catch(error => console.error('Erro ao carregar o clima:', error));
    }
  };

  const getWeatherIcon = (condition) => {
    switch (condition) {
      case 'Rain, Partially cloudy':
      case 'Rain':
        return <FaCloudRain size={40} color="#1e90ff" />;
      case 'Clear':
        return <FaSun size={40} color="#ffcc00" />;
      case 'Partially cloudy':
        return <FaCloudSun size={40} color="#f4a300" />;
      default:
        return <FaCloud size={40} color="#808080" />;
    }
  };

  return (
    <div className="home" style={{ display: 'flex', gap: '2rem' }}>
      <div className="left-side" style={{ flex: 1 }}>
        <h2>Selecione o Restaurante e Data</h2>
        <div>
          <label htmlFor="restaurant">Restaurante:</label>
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
        <div>
          <label htmlFor="restaurantDate">Data (ementa):</label>
          <input type="date" id="restaurantDate" value={restaurantDate} onChange={(e) => setRestaurantDate(e.target.value)} />
        </div>
        <button onClick={fetchMeals}>Confirmar Restaurante/Data</button>

        <h3>Ementa</h3>
        <ul>
          {meals.length > 0 ? (
            meals.map((meal) => (
              <li key={meal.id}>
                <h4>{meal.name}</h4>
                <p>{meal.description}</p>
              </li>
            ))
          ) : (
            <p>Selecione um restaurante e uma data e clique em "Confirmar" para ver a ementa.</p>
          )}
        </ul>
      </div>

      <div className="weather-container" style={{ flex: '1', padding: '20px', borderRadius: '10px', background: '#f0f8ff', boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)' }}>
        <h2>Previsão do Tempo</h2>
        <div>
          <label htmlFor="city">Cidade:</label>
          <input 
            type="text" 
            id="city" 
            value={city} 
            onChange={(e) => setCity(e.target.value)} 
            placeholder="Digite o nome da cidade" 
            style={{ padding: '8px', borderRadius: '4px', width: '200px' }}
          />
        </div>
        <div>
          <label htmlFor="weatherDate">Data (clima):</label>
          <input 
            type="date" 
            id="weatherDate" 
            value={weatherDate} 
            onChange={(e) => setWeatherDate(e.target.value)} 
            style={{ padding: '8px', borderRadius: '4px', width: '200px' }} 
          />
        </div>
        <button 
          onClick={fetchWeather} 
          style={{ padding: '10px 20px', marginTop: '10px', borderRadius: '5px', backgroundColor: '#008CBA', color: 'white', border: 'none' }}
        >
          Confirmar Cidade/Data
        </button>

        {weather && (
          <div style={{ marginTop: '1rem' }}>
            <h3>Previsão para {city} em {weatherDate}</h3>

            <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
              {weather.map((hour, index) => (
                <div key={index} style={{ backgroundColor: '#ffffff', padding: '15px', borderRadius: '10px', boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)', color: '#333' }}>
                  <h4>{new Date(hour.datetime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</h4>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
                    {getWeatherIcon(hour.conditions)}
                    <div>
                      <p><strong>Temperatura: </strong>{hour.temp}°C</p>
                      <p><strong>Condições: </strong>{hour.conditions}</p>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}
      </div>

      <div className="scroll-container" style={{ flex: '1', padding: '20px', maxHeight: '80vh', overflowY: 'auto', position: 'relative' }}>
        {weather && (
          <div>
            <h3>Previsão Horária:</h3>
            <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
              {weather.map((hour, index) => (
                <div key={index} style={{ backgroundColor: '#ffffff', padding: '15px', borderRadius: '10px', boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)', color: '#333' }}>
                  <h4>{new Date(hour.datetime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</h4>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
                    {getWeatherIcon(hour.conditions)}
                    <div>
                      <p><strong>Temperatura: </strong>{hour.temp}°C</p>
                      <p><strong>Condições: </strong>{hour.conditions}</p>
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
