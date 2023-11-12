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

const data = {
    arte:{
        name: "Arte",
        coords:[41.99491581140953, 21.42130350535809],
        desc: "1",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    zograf:{
        name: "Zograf",
        coords: [41.99730016371808, 21.427663092023362],
        desc: "2",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    daleria:{
        name: "Daleria",
        coords: [42.00195409802084, 21.42461300829461],
        desc: "3",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    bezi:{
        name: "Bezi",
        coords: [41.994670891767875, 21.43045730630835],
        desc: "4",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    martin:{
        name: "Martin",
        coords: [41.99363045955053, 21.425193531866014],
        desc: "5",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    mart:{
        name: "Mart",
        coords: [42.01100546381839, 21.40570773620265],
        desc: "6",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    kol:{
        name: "Kol",
        coords: [41.996086039694575, 21.431969833161013],
        desc: "7",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    gral:{
        name: "Gral",
        coords: [41.99747568687593, 21.425561383907795],
        desc: "8",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    pioner:{
        name: "Pioner",
        coords: [41.996525459498166, 21.40680432224659],
        desc: "9",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    mala:{
        name: "Mala",
        coords: [42.002083516549824, 21.42366768038797],
        desc: "10",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    artida:{
        name: "Artida",
        coords: [41.99934938805174, 21.42391253164884],
        desc: "11",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    citygal:{
        name: "Citygal",
        coords: [41.9975394794109, 21.425443369807002],
        desc: "12",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    d3:{
        name: "D3",
        coords: [42.00140348595888, 21.412576164042584],
        desc: "13",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    malstanci:{
        name: "Malstanci",
        coords: [41.99154778918674, 21.424894254800908],
        desc: "14",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    anago:{
        name: "Anago",
        coords:[41.99413749653703, 21.41566834950312],
        desc: "15",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    anju:{
        name: "Anju",
        coords:[41.999952364682294, 21.497607958477726],
        desc: "16",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    bukefal:{
        name: "Bukefal",
        coords:[41.114552018256965, 20.80021393874093],
        desc: "17",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    dudan:{
        name: "Dudan",
        coords:[41.110054283305445, 20.805640196713345],
        desc: "18",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    },
    emanuel:{
        name: "Emanuel",
        coords:[41.113900756374626, 20.799741633820744],
        desc: "19",
        src: "/images/326554246_606384704828028_7636741843512573046_n.jpg"
    }
}

var img = document.createElement('img');
img.width = 200;
img.height = 200;

for (let key in data){
    const artgal = data[key]
    let marker = L.marker(artgal.coords,{

    }).addTo(mapa);
    img.src = artgal.src;
    marker.bindPopup(artgal.name + '\n' + artgal.desc + img.outerHTML);

}

