import React from "react";
import { api_request } from "../js/api";

export default class Login extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			current_stage: -0xdeadbeef,
			auth_text: ""
		}

		if (localStorage.getItem("token")) {
			api_request("/api/v2/login/check").then(data => {
				data = JSON.parse(data);

				if (data.msg == "ok") {
					this.state.current_stage = -1;
					this.setState(this.state);
				} else {
					localStorage.removeItem("token");
					this.state.current_stage = 0;
					this.setState(this.state);
				}
			});
		} else {
			this.state.current_stage = 0;
			this.setState(this.state);
		}
	}

	render() {
		return (
			<div>
				{ this.state.current_stage == -1 ? <div>
					<div className="row-padding dark-grey padding-64 container">
						<div className="content">
							<div className="twothird">
								<p>You are already logged in to log out please use the below button!</p>
								<button className="button grey hover-black" onClick={
									event => {
										localStorage.removeItem("token");
										location.reload();
									}
								}>LogOut</button>
							</div>
						</div>
					</div>
				</div> : null }

				{ this.state.current_stage == 0 ? <div>
					<div className="row-padding dark-grey padding-64 container">
						<div className="content">
							<div className="twothird">
								<p>Hello and Welcome to the authentication page!</p>
								<p>I will guide you trough the whole process!</p>
								<button className="button grey hover-black" onClick={
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
								}>Next</button>
							</div>
						</div>
					</div>
				</div> : null }

				{ this.state.current_stage == 1 ? <div>
					<div className="row-padding dark-grey padding-64 container">
						<div className="content">
							<div className="twothird">
								<p style={{
									display: "inline"
								}}>Please send "<code>{this.state.auth_text}</code>" to the bot on your preferred platform!</p>
							</div>
						</div>
					</div>
				</div> : null }
			</div>
		)
	}
}