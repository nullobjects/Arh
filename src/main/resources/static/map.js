let options = {
    center:[41.607, 21.753],
    zoom:9,
    minZoom:9,
    icon:"/images/marker-icon.png",
    shadowUrl:"/images/marker-shadow-icon.png",
}

let mapa = new L.map('map', options);
let layer = new L.TileLayer('https://tile.thunderforest.com/atlas/{z}/{x}/{y}.png?apikey=2a2a8fb8338646f1bfecaefa5e7de596');
mapa.addLayer(layer);

L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(mapa);

mapa.setMaxBounds([
    [42.472,24.082],
    [40.647,19.841],
]);

var img = document.createElement('img');
img.width = 200;
img.height = 200;

let mapMarkers = {};
fetch("http://localhost:8080/api/GetMarkers")
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        return response.json();
    })
    .then(data => {
        mapMarkers = data;

        for (let key in data){
            const artgal = data[key]
            let marker = L.marker([artgal.x_coord, artgal.y_coord],{

            }).addTo(mapa);
            img.src = artgal.image_url;
            marker.bindPopup(artgal.name + '\n' + artgal.description + img.outerHTML);
        }
    })
    .catch(error => {
        console.error("Couldn't get the map markers:", error);
    });