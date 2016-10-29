import React from 'react';

class TweetForm extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			steganographyImageURL: ''
		}
	}

	updateUsername(e) {
		this.setState({
			Username: e.target.value
		});
	}

	updateMessage(e) {
		this.setState({
			Message: e.target.value
		});
	}

	updateImageURL(e) {
		this.setState({
			imageURL: e.target.value
		});
	}

	submitTweet(e) {
		e.preventDefault();

		fetch('http://hackmcr16-api.azurewebsites.net/api/image/create', {
			method: 'POST',
			body: JSON.stringify(this.state),
			headers: {
				'Content-Type': 'application/json',
			}
		})
		.then(function(response) {
			response.json().then(function(json) {
				this.setState({
					steganographyImageURL: 'data:image/png;base64,' + json.Image
				});
			}.bind(this));
		}.bind(this));



	}

	render() {
		return (
			<div>
				<h1>Request</h1>
				<form onSubmit={this.submitTweet.bind(this)} className="tweet-form">
					<div className="form-group">
						<label>Username</label>
						<input type="text" onChange={this.updateUsername.bind(this)} />
					</div>

					<div className="form-group">
						<label>Message</label>
						<input type="text" onChange={this.updateMessage.bind(this)} />
					</div>

					<div className="form-group">
						<label>Image URL</label>
						<input type="text" onChange={this.updateImageURL.bind(this)} />
					</div>

					<div className="form-group">
						<input type="submit" value="Send message" />
					</div>
				</form>
				<h1>Response</h1>
				<img src={this.state.steganographyImageURL} />
			</div>
		);
	}
}

export default TweetForm;