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
let markerComments = {};

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
            marker.name = artgal.name;
            marker.city = artgal.city;
            marker.comments = artgal.comments;
            let commentForm = `
                <div id="commentForm">
                    <form onsubmit="event.preventDefault(); addMarkerComment('${marker.name}')">
                        <div>
                            <a class="comment">Add a comment:</a><br>
                            <textarea id="markerComment" style="font-family: sans-serif; font-size: 1.2em; resize: none;" placeholder="How would you describe this place?"></textarea>
                        </div>
                        <input type="submit" value="Submit">
                    </form>
                </div>
            `;
            commentForm += '<br><div class="scrollable-div">';
            commentForm += '<p id="default_notice">' + "You can only post comments if you are logged in." + '</p>'

            for (let i = 0; i < marker.comments.length; i++) {
                commentForm += '<p>' + marker.comments[i] + '</p>';
            }
            commentForm += '</div>';

            marker.bindPopup(
                artgal.name + "<br>" +
                artgal.description + "<br>" +
                img.outerHTML + "<br>" +
                "<div style='display: flex;'>" +
                "<a class=\"twitter-share-button\" href=\"https://twitter.com/intent/tweet?text=I%20found%20this%20cool%20gallery%20called%20" + artgal.name + "%20check%20it%20out%20here:%20http://localhost:8080/\" data-size=\"large\">" + "Tweet" + "</a>" +
                "<div style='margin-left: 8px;'>" +
                "<div class=\"fb-share-button\" data-href=\"https://github.com/nullobjects/Arh\" data-layout=\"\" data-size=\"\">" +
                "<a target=\"_blank\" href=\"https://www.facebook.com/sharer/sharer.php?u=https%3A%2F%2Fgithub.com%2Fnullobjects%2FArh&amp;quote=I%20found%20this%20cool%20gallery%20called%20" + artgal.name + "%20check%20it%20out%20here%3A\" class=\"fb-xfbml-parse-ignore\">Share</a>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<a href='javascript:void(0)' id='review' onclick='document.getElementById(\"extras\").hidden=false; document.getElementById(\"review\").hidden=true;'>Review</a>" +
                "<div id='extras' hidden='true'>" +
                commentForm + "<form class=\"star-rating\">\n" +
                "  <input class=\"radio-input\" type=\"radio\" id=\"star5\" name=\"star-input\" value=\"5\" />\n" +
                "  <label class=\"radio-label\" class for=\"star5\" title=\"5 stars\">5 stars</label>\n" +
                "\n" +
                "  <input class=\"radio-input\" type=\"radio\" id=\"star4\" name=\"star-input\" value=\"4\" />\n" +
                "  <label class=\"radio-label\" for=\"star4\" title=\"4 stars\">4 stars</label>\n" +
                "\n" +
                "  <input class=\"radio-input\" type=\"radio\" id=\"star3\" name=\"star-input\" value=\"3\" />\n" +
                "  <label class=\"radio-label\" for=\"star3\" title=\"3 stars\">3 stars</label>\n" +
                "\n" +
                "  <input class=\"radio-input\" type=\"radio\" id=\"star2\" name=\"star-input\" value=\"2\" />\n" +
                "  <label class=\"radio-label\" for=\"star2\" title=\"2 stars\">2 stars</label>\n" +
                "\n" +
                "  <input class=\"radio-input\" type=\"radio\" id=\"star1\" name=\"star-input\" value=\"1\" />\n" +
                "  <label class=\"radio-label\" for=\"star1\" title=\"1 star\">1 star</label>\n" +
                "</form>" + "</div>")

            mapMarkers[mapMarkerCount] = marker;
            mapMarkerCount = mapMarkerCount + 1;
            originalMarkers.push(marker);
            markerComments[artgal.name] = "";

            marker.on('popupopen', function() {
                if (marker.comments.length > 0) {
                    let notice = document.getElementById("default_notice")
                    notice.style.display = 'none';
                }
            });

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
            } else {
                console.log("Empty search")
            }
        }
    }

    document.getElementById('searchText').addEventListener('keypress', handleKeyPress);
});

function searchMarkers(name) {
            let found= false;
            for (let i = 0; i < originalMarkers.length; i++) {
                let marker = originalMarkers[i];
                if (marker.name.toUpperCase().includes(name.toUpperCase())) {
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
}


function CityFilter(city){
            originalMarkers.forEach(marker => {
                marker.addTo(mapa);
            });
            for (let i = 0; i < originalMarkers.length; i++) {
                let marker = originalMarkers[i];
                if (marker.city !== city)
                {
                    mapa.removeLayer(marker);
                }
            }
}

function addMarkerComment(marker_name) {
    let commentText = document.getElementById('markerComment').value;
    mapa.closePopup();

    fetch('/add_comment?comment=' + encodeURIComponent(commentText) + '&name=' + encodeURIComponent(marker_name), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then(response => {
        if (response.redirected) {
            window.location.href = response.url;
        }
    })
        .catch(error => {
            console.error('Error during fetch:', error);
        });
}