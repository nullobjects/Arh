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
let originalMarkers = [];

L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(mapa);

mapa.setMaxBounds([
    [42.472,24.082],
    [40.647,19.841],
]);

var img = document.createElement('img');
img.width = 100;
img.height = 100;

let mapMarkers = {};
let mapMarkerCount = 0;

fetch("http://localhost:8080/api/GetMarkers")
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        return response.json();
    })
    .then(data => {
        mapMarkers = {}
        mapMarkerCount = 0;
        for (let key in data){
            const artgal = data[key]
            let marker = L.marker([artgal.x_coord, artgal.y_coord], {});
            img.src = artgal.image_url;
            marker.bindPopup(artgal.name + "<br>" + artgal.description + "<br>" + img.outerHTML + "<br>" +
                "<a class=\"twitter-share-button\"\n" +
                "href=\"https://twitter.com/intent/tweet?text=I%20found%20this%20cool%20gallery%20called%20" + artgal.name + "%20check%20it%20out%20here:%20http://localhost:8080/\"\n" +
                "data-size=\"large\">\n" + "Tweet" + "</a>" +
                "<div class=\"fb-share-button\" data-href=\"https://github.com/nullobjects/Arh\" data-layout=\"\" data-size=\"\">" +
                "<a target=\"_blank\" href=\"https://www.facebook.com/sharer/sharer.php?u=https%3A%2F%2Fgithub.com%2Fnullobjects%2FArh&amp;quote=I%20found%20this%20cool%20gallery%20called%20" + artgal.name + "%20check%20it%20out%20here%3A\" class=\"fb-xfbml-parse-ignore\">Share</a>" +
                "</div>");
            marker.name = artgal.name;
            mapMarkers[mapMarkerCount] = marker;
            mapMarkerCount = mapMarkerCount + 1
            originalMarkers.push(marker);
        }
        originalMarkers.forEach(marker => {
            marker.addTo(mapa);
        });
    })
    .catch(error => {
        console.error("Couldn't get the map markers:", error);
    });

document.addEventListener('DOMContentLoaded', function() {
    function handleKeyPress(event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            originalMarkers.forEach(marker => {
                marker.addTo(mapa);
            });
            let searchQuery = document.getElementById('searchText').value;
            if (searchQuery.trim() !== ""){
            searchMarkers(searchQuery)
                console.log(searchQuery);
            } else {
                console.log("empty")
            }
        }
    }
    document.getElementById('searchText').addEventListener('keypress', handleKeyPress);
});

function searchMarkers(name) {
    fetch('/search?name=' + name)
        .then(response => response.json())
        .then(data => {
            let found=false;
            for (let i = 0; i < originalMarkers.length; i++) {
                let marker = originalMarkers[i];
                if (marker.name === data.name) {
                    found = true;
                } else {
                    mapa.removeLayer(marker);
                }
            }
            if (!found)
            {
                originalMarkers.forEach(marker => {
                    mapa.removeLayer(marker);
                    });
                }
        })
        .catch(error => console.error('Error:', error));
}

