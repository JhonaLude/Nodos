from flask import Flask, render_template, request, jsonify
import requests
import json
import time
from datetime import datetime

app = Flask(__name__)

# Configuración de la API
API_BASE_URL = "http://localhost:8080/api"  # Ajusta el puerto según tu configuración de Java

# Ruta principal - Renderiza la interfaz
@app.route('/')
def index():
    return render_template('index.html')

# API para obtener todas las ubicaciones
@app.route('/api/ubicaciones')
def get_ubicaciones():
    try:
        response = requests.get(f"{API_BASE_URL}/ubicacion/list")
        return jsonify(response.json())
    except requests.RequestException as e:
        return jsonify({"msg": "ERROR", "error": str(e)}), 500

# API para obtener todas las rutas
@app.route('/api/rutas')
def get_rutas():
    try:
        response = requests.get(f"{API_BASE_URL}/ruta/list")
        return jsonify(response.json())
    except requests.RequestException as e:
        return jsonify({"msg": "ERROR", "error": str(e)}), 500

# API para agregar una nueva ubicación
@app.route('/api/ubicacion/save', methods=['POST'])
def save_ubicacion():
    try:
        data = request.json
        response = requests.post(
            f"{API_BASE_URL}/ubicacion/save",
            json=data
        )
        return jsonify(response.json())
    except requests.RequestException as e:
        return jsonify({"msg": "ERROR", "error": str(e)}), 500

# API para agregar una nueva ruta
@app.route('/api/ruta/save', methods=['POST'])
def save_ruta():
    try:
        data = request.json
        response = requests.post(
            f"{API_BASE_URL}/ruta/save",
            json=data
        )
        return jsonify(response.json())
    except requests.RequestException as e:
        return jsonify({"msg": "ERROR", "error": str(e)}), 500

# API para encontrar el camino más corto
@app.route('/api/graph/shortestPath')
def find_shortest_path():
    try:
        start = request.args.get('start')
        end = request.args.get('end')
        response = requests.get(
            f"{API_BASE_URL}/graph/shortestPath",
            params={'start': start, 'end': end}
        )
        return jsonify(response.json())
    except requests.RequestException as e:
        return jsonify({"msg": "ERROR", "error": str(e)}), 500

# API para análisis de rendimiento
@app.route('/api/graph/analyze', methods=['POST'])
def analyze_performance():
    try:
        data = request.json
        size = data.get('size', 10)
        
        # Medir tiempo de Floyd-Warshall
        start_time = time.time()
        floyd_response = requests.get(f"{API_BASE_URL}/graph/floydWarshall")
        floyd_time = (time.time() - start_time) * 1000  # Convertir a milisegundos
        
        # Medir tiempo de Bellman-Ford
        start_time = time.time()
        bellman_response = requests.get(
            f"{API_BASE_URL}/graph/bellmanFord",
            params={'start': 'A'}  # Usando 'A' como nodo inicial por defecto
        )
        bellman_time = (time.time() - start_time) * 1000  # Convertir a milisegundos
        
        # Guardar resultados en un archivo
        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
        results = {
            "timestamp": timestamp,
            "size": size,
            "floydTime": floyd_time,
            "bellmanTime": bellman_time
        }
        
        with open(f'performance_results_{timestamp}.json', 'w') as f:
            json.dump(results, f, indent=2)
        
        return jsonify({
            "msg": "OK",
            "size": size,
            "floydTime": floyd_time,
            "bellmanTime": bellman_time
        })
    except requests.RequestException as e:
        return jsonify({"msg": "ERROR", "error": str(e)}), 500

# API para guardar el grafo en archivo JSON
@app.route('/api/graph/save', methods=['POST'])
def save_graph():
    try:
        filename = request.args.get('filename', 'graph.json')
        response = requests.post(
            f"{API_BASE_URL}/graph/save",
            params={'filename': filename}
        )
        return jsonify(response.json())
    except requests.RequestException as e:
        return jsonify({"msg": "ERROR", "error": str(e)}), 500

# API para cargar el grafo desde archivo JSON
@app.route('/api/graph/load', methods=['POST'])
def load_graph():
    try:
        filename = request.args.get('filename', 'graph.json')
        response = requests.post(
            f"{API_BASE_URL}/graph/load",
            params={'filename': filename}
        )
        return jsonify(response.json())
    except requests.RequestException as e:
        return jsonify({"msg": "ERROR", "error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True, port=5000)