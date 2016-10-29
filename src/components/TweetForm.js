import React from 'react';

class TweetForm extends React.Component {

	constructor(props) {
		super(props);
	}

	updateRecipient(e) {
		this.setState({
			recipient: e.target.value
		});
	}

	updateEncryptedMessage(e) {
		this.setState({
			encryptedMessage: e.target.value
		});
	}

	updateUnencryptedMessage(e) {
		this.setState({
			unencryptedMessage: e.target.value
		});
	}

	updateImageURL(e) {
		this.setState({
			imageURL: e.target.value
		});
	}

	submitTweet(e) {
		e.preventDefault();
		


		// Do something

		

	}

	render() {
		return (
			<form onSubmit={this.submitTweet.bind(this)} className="tweet-form">
				<div className="form-group">
					<label>Recipient</label>
					<input type="text" onChange={this.updateRecipient.bind(this)} />
				</div>
				<div className="form-group">
					<label>Encrypted message</label>
					<input type="text" onChange={this.updateEncryptedMessage.bind(this)} />
				</div>

				<div className="form-group">
					<label>Unencrypted message</label>
					<input type="text" onChange={this.updateUnencryptedMessage.bind(this)} />
				</div>

				<div className="form-group">
					<label>Image URL</label>
					<input type="text" onChange={this.updateImageURL.bind(this)} />
				</div>

				<div className="form-group">
					<input type="submit" value="Send message" />
				</div>
			</form>
		);
	}
}

export default TweetForm;