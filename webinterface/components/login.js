import React from "react";
import { api_request, has_valid_token } from "../js/api";
import Button from "./button";
import Card from "./card";

export default class Login extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			current_stage: -0xdeadbeef,
			auth_text: ""
		}

		has_valid_token().then(data => {
			if (data) {
				this.state.current_stage = -1;
				this.setState(this.state);
			} else {
				this.state.current_stage = 0;
				this.setState(this.state);
			}
		})
	}

	render() {
		return (
			<div>
				{ this.state.current_stage == -1 ? <div>
					<Card>
						<p>You are already logged in to log out please use the below button!</p>
						<Button onClick={
							event => {
								localStorage.removeItem("token");
								location.reload();
							}
						}>LogOut</Button>
					</Card>
				</div> : null }

				{ this.state.current_stage == 0 ? <div>
					<Card>
						<p>Hello and Welcome to the authentication page!</p>
						<p>I will guide you trough the whole process!</p>
						<Button onClick={
							event => {
								api_request("/api/v2/login/start").then(data => {
									data = JSON.parse(data);

									this.state.auth_text = `-auth ${data.id}`;

									this.state.current_stage = 1;
									this.setState(this.state);

									this.interval = setInterval(() => {
										api_request("/api/v2/login/status?login_id=" + data.id).then(data2 => {
											data2 = JSON.parse(data2);

											console.log(data2);

											if (data2.token) {
												clearInterval(this.interval);
												localStorage.setItem("token", data2.token);
												location.reload();
											}
										});
									}, 1000);
								});
							}
						}>Next</Button>
					</Card>
				</div> : null }

				{ this.state.current_stage == 1 ? <div>
					<Card>
						<p style={{
							display: "inline"
						}}>Please send "<code>{this.state.auth_text}</code>" to the bot on your preferred platform!</p>
					</Card>
				</div> : null }
			</div>
		)
	}
}