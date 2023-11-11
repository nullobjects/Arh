let options = {
    center:[41.607, 21.753],
    zoom:9
}

let mapa = new L.map('map', options);
let layer = new L.TileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png');
mapa.addLayer(layer);

//marker
let marker1 = new L.marker([51,21], {
    color: 'red',
    fillColor: '#f03',
    fillOpacity: 0.5,
    radius: 500
}).addTo(mapa)