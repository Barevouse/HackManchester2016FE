class ImageService {
	create(request, callback) {
		fetch('http://hackmcr16-api.azurewebsites.net/api/image/create', {
			method: 'POST',
			body: JSON.stringify(request),
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(function(response) {
			response.json().then(function(json) {
				callback(json);
			})
		});
	}
}

export default ImageService;