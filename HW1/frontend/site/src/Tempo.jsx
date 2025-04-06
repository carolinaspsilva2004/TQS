import './Tempo.css';
import axios from 'axios';
import React, { useState } from 'react';
import { FaSearchLocation, FaCalendarDay } from 'react-icons/fa';
import { FaCloudSun, FaCloudRain, FaSun, FaCloud } from 'react-icons/fa';


const Tempo = () => {
     const [weatherDate, setWeatherDate] = useState('');
    const [weather, setWeather] = useState([]);
    const [weatherSummary, setWeatherSummary] = useState(null);
    const [city, setCity] = useState('');
     
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
        <div className="container">
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
    );
}

export default Tempo;
