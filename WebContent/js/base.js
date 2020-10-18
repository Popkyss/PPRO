
/**
 * Neni vyreseno pro parametry v url
 * pouze vezme stavajici url smaze parametry a zavola ji s novym locale
 * @param $language
 * @param $path
 * @return
 */
function setLocale(language) {
	fullURL = parent.document.URL; 
	http = fullURL;
//	.substring(0, fullURL.length-14);
//	window.alert();
	if(http.indexOf("?")>0){
		if(http.indexOf("request_locale")>0){
			
//			window.location = http.substring(0, http.indexOf("request_locale"))+"request_locale="+language;
			http = 	fullURL.substring(0, http.indexOf("?")-14)+"/locale.action?"+
			http.substring(http.indexOf("?")+1,http.indexOf("request_locale")-1)+
			http.substring(http.indexOf("request_locale")+17, fullURL.length)+
			"&request_locale="+language;
//			window.alert(http);
			window.location = http;
		}
		else{
			act = fullURL.substring(http.indexOf("?")-13, http.indexOf("?"));
			http = 	fullURL.substring(0, http.indexOf("?")-14)+"/locale.action"+
					fullURL.substring(http.indexOf("?"),fullURL.length)+
					"&request_locale="+language+"&action="+act;
//			window.alert(http);
			window.location = http;
		}
	}
	else{
		http = fullURL.substring(0, fullURL.length-14);
		act = fullURL.substring(fullURL.length-13, fullURL.length);
//		window.alert(http+"/locale.action?request_locale="+language+"&action="+act);
//		window.location = http+"?request_locale="+language;
		window.location = http+"/locale.action?request_locale="+language+"&action="+act;
	}
//	act = fullURL.substring(fullURL.length-13, fullURL.length);
//	window.location = http+"/locale.action?request_locale="+language+"&action="+act;
}


function changeLanguageMenu(language){
	var selectElement = document.getElementById('language');
	if(selectElement == null) {
		return;
	}
	
	for (var i = 0; i < selectElement.length; i++) {
		if (language == selectElement.options[i].value) {
			selectElement.options[i].selected = true;
		} else {
			selectElement.options[i].selected = false;
		}
	}
}


function setRadkyNaStrance(pocetRadku) {
	fullURL = parent.document.URL; 
	http = fullURL.substring(fullURL.indexOf('.action'), 0);
	if(http==""){
		http = fullURL.substring(fullURL.indexOf('.public'), 0);
		window.location = http+".public?poctyRadkuNaStranceAction=&pocetRadku="+pocetRadku;
	}else{
		window.location = http+".action?poctyRadkuNaStranceAction=&pocetRadku="+pocetRadku;
	}
}


function setFocus(inputId){
	var input = document.getElementById(inputId); 
	if(input != null){
		if(!input.disabled){
			input.focus();
		}
		input.value = input.value;
	}
}


function disableEnterKey(e) {
	var key;
	if (window.event)
		key = window.event.keyCode; //IE
	else
		key = e.which; //firefox     

	return (key != 13);
}


/**
 * Vrati aktualni datum a cas formatovany dle masky dd.MM.yyyy HH:mm:ss 
 */
function getCurrentDate() {
	var currentdate = new Date(); 
	var den = currentdate.getDate();
	var mesic = (currentdate.getMonth()+1);
	var rok = currentdate.getFullYear();
	var hodina = currentdate.getHours();
	var minuta = currentdate.getMinutes();
	var sekunda = currentdate.getSeconds();
	
	var dateTime = format(den) + "." + format(mesic) + "." + format(rok) + "  " +
					format(hodina) + ":" + format(minuta) + ":" + format(sekunda);
	return dateTime;
}


/**
 * Pomocna funkce pro formatovani cisel pro funkci getCurrentDate
 * @param hodnota hodnota pro formatovani
 * @return hodnota formatovana na dva znaky (muze byt vedouci nula)
 */
function format(hodnota) {
	if(hodnota < 10) {
		return "0" + hodnota;
	} else {
		return "" + hodnota;
	}
}


/**
 * Zapise do spanu, ktery je v paticce aktualni datum a cas
 */
function writeCurrentDate() {
	var date = getCurrentDate();
	var span = document.getElementById("footerCurrentDateId");
	
	if(span != null) {
		span.innerHTML = date;
	}
}

