let options = {
    center:[41.607, 21.753],
    zoom:9,
    minZoom:9
}

let mapa = new L.map('map', options);
let layer = new L.TileLayer('https://tile.thunderforest.com/atlas/{z}/{x}/{y}.png?apikey=2a2a8fb8338646f1bfecaefa5e7de596');
mapa.addLayer(layer);

let marker1 = new L.marker([41.607,21.753], {
    color: 'red',
    fillColor: 'FF0033',
    fillOpacity: 0.5,
    radius: 500
}).addTo(mapa)

mapa.setMaxBounds([
    [42.472,24.082],
    [40.647,19.841],  
]);