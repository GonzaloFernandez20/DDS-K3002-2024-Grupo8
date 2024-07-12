function iniciarMap(){
    var coord1 = {lat:-34.5986303 ,lng: -58.438571};
    var coord2 = {lat:-34.6001493 ,lng: -58.4274559};
    var map = new google.maps.Map(document.getElementById('map'),{
      zoom: 10,
      center: coord1
    });
    var markers = [
        new google.maps.Marker({
          position: coord1,
          map: map,
          title: 'Heladera Medrano'
        }),
        new google.maps.Marker({
          position: coord2,
          map: map,
          title: 'Heladera Subte Medrano'
        }),
        // Agregar más marcadores según sea necesario
    ];
}
