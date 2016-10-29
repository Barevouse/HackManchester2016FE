import React from 'react';

class TweetForm extends React.Component {
	render() {
		return (
			<div className="tweet-form">
				<div className="form-group">
					<label>Recipient</label>
					<input type="text" />
				</div>
				<div className="form-group">
					<label>Encrypted message</label>
					<input type="text" />
				</div>

				<div className="form-group">
					<label>Unencrypted message</label>
					<input type="text" />
				</div>

				<div className="form-group">
					<label>Image URL</label>
					<input type="text" />
				</div>

				<div className="form-group">
					<input type="button" value="Send message" />
				</div>
			</div>
			);
	}
}

export default TweetForm;