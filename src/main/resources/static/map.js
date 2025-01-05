// Define options for initializing the map
let options = {
    center:[41.607, 21.753],
    zoom:9,
    minZoom:9,
    icon:"/images/marker-icon.png",
    shadowUrl:"/images/marker-shadow-icon.png",
}

// Create a new Leaflet map instance
let mapa = new L.map('map', options);

// Add a tile layer to the map using Thunderforest tiles
let layer = new L.TileLayer('https://tile.thunderforest.com/atlas/{z}/{x}/{y}.png?apikey=2a2a8fb8338646f1bfecaefa5e7de596');
mapa.addLayer(layer);

// Define variables for storing markers and comments
let originalMarkers = [];
let markerComments = {};

// Add attribution to OpenStreetMap
L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(mapa);

// Set the maximum bounds for the map
mapa.setMaxBounds([
    [42.472,24.082],
    [40.647,19.841],
]);

let img = document.createElement('img');
img.width = 100;
img.height = 100;
img.id = "marker_image"

// Define variables for storing markers and comments
let mapMarkers = {};
let mapMarkerCount = 0;

// Fetch markers data from the API
fetch("http://localhost:8080/api/GetMarkers")
    .then(response => {
        // Check if response is OK
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        // Parse response JSON
        return response.json();
    })
    .then(data => {
        // Process retrieved data
        mapMarkers = {}
        mapMarkerCount = 0;
        for (let key in data){
            const artgal = data[key]
            let marker = L.marker([artgal.x_coord, artgal.y_coord], {});
            img.src = artgal.image_url;
            marker.name = artgal.name;
            marker.city = artgal.city;
            marker.comments = artgal.comments;
            // Define HTML content for marker popup
            let commentForm = `
                <div id="commentForm">
                    <form onsubmit="event.preventDefault(); addMarkerComment('${marker.name}')">
                        <div>
                            <a id="add_comment">Add a comment:</a><br>
                            <textarea id="markerComment" placeholder="How would you describe this place?"></textarea>
                        </div>
                        <input id = "submit_comment" type="submit" value="Submit">
                    </form>
                </div>
            `;
            commentForm += '<div class="scrollable-div">';
            commentForm += '<p id="default_notice">' + "You can only post comments if you are logged in." + '</p>'

            for (let i = 0; i < marker.comments.length; i++) {
                commentForm += '<p>' + marker.comments[i].user.username + ": " + marker.comments[i].content + '</p>';
            }
            commentForm += '</div>';

            // Bind popup content to marker
            marker.bindPopup(
                img.outerHTML + "<div id = \"marker_bottom\"><div id = \"marker_bottom_content\">" +
                "<span id = \"marker_name\">" + artgal.name + "</span>" +
                "<span id = \"marker_desc\">" + artgal.description + "</span>" +
                "<div id = \"marker_share\">" +
                    "<a class=\"share-button\" href=\"https://twitter.com/intent/tweet?text=I%20found%20this%20cool%20gallery%20called%20" + artgal.name + "%20check%20it%20out%20here:%20http://localhost:8080/\" data-size=\"large\">" + "Tweet" + "</a>" +
                    "<a class = \"fb-xfbml-parse-ignore share-button\" target=\"_blank\" href=\"https://www.facebook.com/sharer/sharer.php?u=https%3A%2F%2Fgithub.com%2Fnullobjects%2FArh&amp;quote=I%20found%20this%20cool%20gallery%20called%20" + artgal.name + "%20check%20it%20out%20here%3A\">Share</a>" +
                "</div>" +
                "<a href='javascript:void(0)' id='review' onclick='document.getElementById(\"extras\").hidden=false; document.getElementById(\"review\").hidden=true; document.getElementById(\"review\").display=none;'>Review</a>" +
                "<div id='extras' hidden='true'>" + "<form class=\"star-rating\">\n" +
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
                "</form>" +
                commentForm + "</div></div></div>")

            // Add marker to map and store in variables
            mapMarkers[mapMarkerCount] = marker;
            mapMarkerCount = mapMarkerCount + 1;
            originalMarkers.push(marker);
            markerComments[artgal.name] = "";

            // Event listener for opening popup
            marker.on('popupopen', function() {
                if (marker.comments.length > 0) {
                    let notice = document.getElementById("default_notice")
                    notice.style.display = 'none';
                }
            });

        }
        // Add markers to the map
        originalMarkers.forEach(marker => {
            marker.addTo(mapa);
        });
    })
    .catch(error => {
        console.error("Couldn't get the map markers:", error);
    });

// Event listener for key press on search input
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

// Function to search markers by name
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

// Function to filter markers by city
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
// Function to add a comment to a marker
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

// Dark/Light mode function
let count = 0;
function changeTheme() {
    let theme_switch = document.getElementById("theme-change")
    let logo = document.getElementById("nullLogo")
    let map_element = document.getElementsByClassName("leaflet-layer ")
    switch (count) {
        case 0:
            theme_switch.src = "/images/dark_mode.png";
            logo.src="/images/logo/nullobjects_black.png"
            map_element[1].style.filter= "invert(0%) hue-rotate(25deg) brightness(100%) contrast(100%)"
            count = 1;
            break;
        case 1:
            theme_switch.src = "/images/light_mode.png";
            logo.src="/images/logo/nullobjects_white.png"
            map_element[1].style.filter= "invert(100%) hue-rotate(200deg) brightness(100%) contrast(90%)"
            count = 0;
            break;
    }
}

function changePage(){
    window.open("https://github.com/nullobjects/Arh", "_blank");
}