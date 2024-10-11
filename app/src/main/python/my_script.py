import gym

def run_taxi_episode():
    env = gym.make("Taxi-v3")
    state = env.reset()
    done = False
    steps = []
    total_reward = 0

    while not done:
        action = env.action_space.sample()  # Random action, replace with your RL agent
        next_state, reward, done, _ = env.step(action)
        total_reward += reward
        steps.append({
            "state": state,
            "action": action,
            "next_state": next_state,
            "reward": reward
        })
        state = next_state

    return {"steps": steps, "total_reward": total_reward}
