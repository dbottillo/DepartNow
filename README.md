# Welcome !

This small application is made of an home screen with two tabs and a button to show the train departures from a specific train station in London (UK) using the [TransportAPI](https://developer.transportapi.com/). For obvious reasons, key & app_id are not part of the project so if you want to run the project you would need to add those two keys in the `local.properties` file:
```
    
transportapi_app_key=<value>
transportapi_app_id=<value>
    
```

<p float="center">
    <img src="home_base_project.png" width="200" />
    <img src="train_bus_departures.png" width="200" /> 
</p>

The idea of the train departure app is to be used as a full screen display on a standing phone to inform about the next departures of the nearby station & bus stop. 
The API comes with 30 free api requests a day so instead of having some form of automatic refresh, it will just react on touching the screen instead.