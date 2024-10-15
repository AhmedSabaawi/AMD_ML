import gym

def create_taxi_env():
    # Create the Taxi-v3 environment
    env = gym.make('Taxi-v3')
    return env

def reset_taxi_env(env):
    # Reset the environment to the initial state
    state = env.reset()
    return state

def step_taxi_env(env, action):
    # Take a step in the environment
    next_state, reward, done, info = env.step(action)
    return next_state, reward, done, info

def render_taxi_env(env):
    # Render the environment as a string (for textual output)
    return env.render()
